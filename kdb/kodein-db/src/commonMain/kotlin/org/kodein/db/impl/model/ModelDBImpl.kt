package org.kodein.db.impl.model

import org.kodein.db.*
import org.kodein.db.data.DataDB
import org.kodein.db.impl.utils.*
import org.kodein.db.model.*
import org.kodein.db.model.orm.HasMetadata
import org.kodein.db.model.orm.Metadata
import org.kodein.db.model.orm.MetadataExtractor
import org.kodein.db.model.orm.Serializer
import org.kodein.memory.Closeable
import org.kodein.memory.io.*
import org.kodein.memory.text.readString
import org.kodein.memory.use
import org.kodein.memory.util.deferScope
import org.kodein.memory.util.forEachResilient
import kotlin.reflect.KClass

internal class ModelDBImpl(
    private val defaultSerializer: Serializer<Any>?,
    userClassSerializers: Map<KClass<*>, Serializer<*>>,
    private val metadataExtractors: List<MetadataExtractor>,
    internal val valueConverters: List<ValueConverter>,
    internal val typeTable: TypeTable,
    override val data: DataDB
) : ModelDB, ModelReadModule, ModelWriteModule, Closeable by data {

    private val listenersLock = newRWLock()
    private val listeners = LinkedHashSet<DBListener<Any>>()

    private val typeLock = newLock()
    private var nextTypeId: Int? = null
    private val typeNameMap = HashMap<ReadMemory, Int>()
    private val typeIdMap = HashMap<Int, ReadMemory>()

    private val typeCache = HashMap<Int, KClass<*>>()

    @OptIn(ExperimentalStdlibApi::class)
    private val classSerializers = buildMap<KClass<*>, Serializer<*>> {
        putAll(userClassSerializers)
        put(IntPrimitive::class, IntPrimitive.S)
        put(LongPrimitive::class, LongPrimitive.S)
        put(DoublePrimitive::class, DoublePrimitive.S)
        put(StringPrimitive::class, StringPrimitive.S)
        put(BytesPrimitive::class, BytesPrimitive.S)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun serialize(model: Any, output: Writeable, vararg options: Options.Write) =
            (classSerializers[model::class] as? Serializer<Any>)?.serialize(model, output, *options)
                    ?: defaultSerializer?.serialize(model, output, *options)
                    ?: throw IllegalArgumentException("No serializer found for type ${model::class}")

    internal fun getListeners() = listenersLock.readLock().withLock { listeners.toList() }

    private fun <T> writeOnListeners(action: MutableSet<DBListener<Any>>.() -> T) = listenersLock.writeLock().withLock { listeners.action() }

    override fun willAction(action: DBListener<Any>.() -> Unit) = getListeners().forEach(action)

    override fun didAction(action: DBListener<Any>.() -> Unit) = getListeners().forEachResilient(action)

    internal fun getMetadata(model: Any, options: Array<out Options.Write>): Metadata {
        (model as? HasMetadata)?.getMetadata(this, *options)?.let { return it }

        metadataExtractors.forEach {
            it.extractMetadata(model, *options)?.let { return it }
        }

        error("Models does not implement neither HasMetadata nor Metadata, and no MetadataExtractor could extract metadata for $model")
    }

    override val mdb: ModelDBImpl get() = this

    override fun newBatch(): ModelBatch = ModelBatchImpl(this, data.newBatch())

    override fun newSnapshot(vararg options: Options.Read): ModelSnapshot = ModelSnapshotImpl(this, data.newSnapshot())

    override fun register(listener: DBListener<Any>): Closeable {
        val subscription = Closeable { writeOnListeners { remove(listener) } }
        if (writeOnListeners { add(listener) }) listener.setSubscription(subscription)
        return subscription
    }

    internal fun getTypeId(typeName: ReadMemory, createIfNone: Boolean = true): Int {
        typeNameMap[typeName]?.let { return it }

        deferScope {
            lockInScope(typeLock)

            typeNameMap[typeName]?.let { return it }

            val typeNameKey = Allocation.native(getTypeNameKeySize(typeName)) { putTypeNameKey(typeName) }.useInScope()

            val existingTypeId = data.ldb.get(typeNameKey)?.use { it.getInt(0) }
            if (existingTypeId != null) {
                typeNameMap[typeName] = existingTypeId
                typeIdMap[existingTypeId] = typeName
                return existingTypeId
            }

            if (!createIfNone) return 0

            val newTypeId = nextTypeId ?: data.ldb.get(nextTypeKey)?.use { it.getInt(0) } ?.also { nextTypeId = it } ?: 1
            check(newTypeId != 0) { "No more type int available. Have you inserted UINT_MAX different types in this database ?!?!?!" }
            val typeIdKey = Allocation.native(typeIdKeySize) { putTypeIdKey(newTypeId) } .useInScope()
            data.ldb.newWriteBatch().use {
                it.put(typeNameKey, Memory.array(4) { writeInt(newTypeId) })
                it.put(typeIdKey, typeName)
                it.put(nextTypeKey, Memory.array(4) { writeInt(newTypeId + 1) })
                data.ldb.write(it)
            }
            typeNameMap[typeName] = newTypeId
            typeIdMap[newTypeId] = typeName
            nextTypeId = newTypeId + 1
            return newTypeId
        }
    }

    internal fun getTypeName(typeId: Int): ReadMemory? {
        typeIdMap[typeId]?.let { return it }
        deferScope {
            lockInScope(typeLock)
            val typeIdKey = Allocation.native(typeIdKeySize) { putTypeIdKey(typeId) } .useInScope()
            return data.ldb.get(typeIdKey)?.use { alloc ->
                Memory.arrayCopy(alloc).also { typeName ->
                    typeNameMap[typeName] = typeId
                    typeIdMap[typeId] = typeName
                }
            }
        }
    }

    internal fun <M : Any> deserialize(type: KClass<out M>, transientId: ReadMemory, body: ReadMemory, options: Array<out Options.Read>): Sized<M> {
        val r = body.asReadable()

        val typeId = r.readInt()
        val realType = typeCache[typeId] ?: run {
            val typeName = getTypeName(typeId) ?: throw IllegalStateException("Unknown type ID. Has this LevelDB entry been inserted outside of Kodein DB?")
            typeTable.getTypeClass(typeName) ?: run {
                check(type != Any::class) { "Type ${typeName.readString()} is not declared in type table." }
                val expectedTypeName = typeTable.getTypeName(type)
                check(typeName.compareTo(expectedTypeName) == 0) { "Type ${typeName.readString()} is not declared in type table and do not match expected type ${expectedTypeName.readString()}." }
                type
            }.also { typeCache[typeId] = it }
        }

        @Suppress("UNCHECKED_CAST")
        realType as KClass<M>

        val size = r.remaining

        @Suppress("UNCHECKED_CAST")
        val model = ((classSerializers[realType] as? Serializer<Any>)?.deserialize(realType, transientId, r, *options)
            ?: defaultSerializer?.deserialize(realType, transientId, r, *options)
            ?: throw IllegalArgumentException("No serializer found for type $realType")) as M

        check (r.remaining == 0) { "Deserializer has not consumed the entire body (left ${r.remaining} bytes)" }

        return Sized(model, size)
    }

    override fun <T : Any> getExtension(key: ExtensionKey<T>): T? = data.getExtension(key)

}

package org.kodein.db.impl.model

import org.kodein.db.*
import org.kodein.db.data.DataWrite
import org.kodein.db.impl.data.writeDocumentKey
import org.kodein.db.model.ModelWrite
import org.kodein.db.model.orm.Metadata
import org.kodein.memory.io.ReadMemory
import org.kodein.memory.io.asReadable
import org.kodein.memory.io.verify
import kotlin.reflect.KClass

internal interface ModelWriteModule : ModelKeyMakerModule, ModelWrite {

    override val data: DataWrite

    fun willAction(action: DBListener<Any>.() -> Unit)

    fun didAction(action: DBListener<Any>.() -> Unit)

    private fun <M: Any> put(model: M, options: Array<out Options.Write>, block: (ReadMemory, Metadata) -> Key<M>): KeyAndSize<M> {
        val metadata = mdb.getMetadata(model, options)
        val typeName = mdb.typeTable.getTypeName(model::class)
        val rootTypeName = mdb.typeTable.getTypeName(mdb.typeTable.getRootOf(model::class) ?: model::class)
        willAction { willPut(model, rootTypeName, metadata, options) }
        val body = Body { body ->
            val typeId = mdb.getTypeId(typeName)
            body.writeInt(typeId)
            mdb.serialize(model, body, *options)
        }
        val key = block(rootTypeName, metadata)
        // TODO support multiple values
        val indexMap = metadata.indexes().mapValues { listOf<Pair<Value, Body?>>(valueOf(it.value) to null) }
        val size = data.put(key.bytes, body, indexMap, *options)
        didAction { didPut(model, key, rootTypeName, metadata, size, options) }
        return KeyAndSize(key, size)
    }

    override fun <M : Any> put(model: M, vararg options: Options.Write): KeyAndSize<M> =
        put(model, options) { rootTypeName, metadata ->
            val key = Key<M>(data.newKey(mdb.getTypeId(rootTypeName), valueOf(metadata.id)))
            key
        }

    override fun <M : Any> put(key: Key<M>, model: M, vararg options: Options.Write): Int =
        put(model, options) { rootTypeName, metadata ->
            verify(key.bytes.asReadable()) { writeDocumentKey(mdb.getTypeId(rootTypeName), valueOf(metadata.id)) }
            key
        }.size

    override fun <M: Any> delete(type: KClass<M>, key: Key<M>, vararg options: Options.Write) {
        var fetched = false
        var model: Any? = null
        val getModel: () -> Any? = {
            if (!fetched) {
                model = mdb.get(type, key)?.model
                fetched = true
            }
            model
        }
        val rootTypeName = mdb.typeTable.getTypeName(mdb.typeTable.getRootOf(type) ?: type)
        willAction { willDelete(key, getModel, rootTypeName, options) }
        data.delete(key.bytes, *options)
        didAction { didDelete(key, model, rootTypeName, options) }
    }
}

package org.kodein.db.impl.data

import org.kodein.db.*
import org.kodein.db.data.DataBatch
import org.kodein.db.data.DataIndexMap
import org.kodein.db.impl.utils.withLock
import org.kodein.db.leveldb.LevelDB
import org.kodein.memory.closeAll
import org.kodein.memory.io.*
import org.kodein.memory.use
import org.kodein.memory.util.MaybeThrowable
import org.kodein.memory.util.forEachCatchTo

internal class DataBatchImpl(private val ddb: DataDBImpl) : DataKeyMakerModule, DataBatch {

    private val batch = ddb.ldb.newWriteBatch()

    private val deleteRefKeys = ArrayList<Allocation>()

    private fun put(body: Body, documentKey: ReadMemory, indexes: DataIndexMap): Int {
        val bodySize = ExpandableAllocation.native(DataDBImpl.DEFAULT_CAPACITY) { writeBody(body) } .use { bodyMemory ->
            batch.put(documentKey, bodyMemory)
            bodyMemory.size
        }

        val refKey = Allocation.native(documentKey.size) { writeRefKeyFromDocumentKey(documentKey) }
        ddb.putIndexesInBatch(batch, documentKey, refKey, indexes)

        deleteRefKeys += refKey

        return bodySize
    }

    override fun put(key: ReadMemory,  body: Body, indexes: DataIndexMap, vararg options: Options.Write): Int {
        key.verifyDocumentKey()
        return put(body, key, indexes)
    }

    override fun delete(key: ReadMemory, vararg options: Options.Write) {
        key.verifyDocumentKey()
        batch.delete(key)
        val refKey = Allocation.native(key.size) { writeRefKeyFromDocumentKey(key) }
        deleteRefKeys += refKey
    }

    override fun write(afterErrors: MaybeThrowable, vararg options: Options.Write) {
        val anticipations = options.all<Anticipate>()
        val inLockAnticipations = options.all<AnticipateInLock>()
        val inLockReactions = options.all<ReactInLock>()
        val reactions = options.all<React>()

        this.use {
            anticipations.forEach { it.block() }
            ddb.ldb.newWriteBatch().use { fullBatch ->
                ddb.lock.withLock {
                    inLockAnticipations.forEach { it.block(batch) }
                    deleteRefKeys.forEach { ddb.deleteIndexesInBatch(fullBatch, it) }
                    fullBatch.append(batch)
                    ddb.ldb.write(fullBatch, LevelDB.WriteOptions.from(options))
                    inLockReactions.forEachCatchTo(afterErrors) { it.block(-1) }
                }
            }
        }
        reactions.forEachCatchTo(afterErrors) { it.block(-1) }
    }

    override fun close() {
        (deleteRefKeys + batch).closeAll()
    }
}

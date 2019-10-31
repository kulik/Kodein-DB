package org.kodein.db.impl.data

import org.kodein.db.*
import org.kodein.db.data.DataBatch
import org.kodein.db.data.DataDB
import org.kodein.db.data.DataSnapshot
import org.kodein.db.data.DataWrite
import org.kodein.db.impl.Check
import org.kodein.db.impl.utils.newLock
import org.kodein.db.impl.utils.putBody
import org.kodein.db.impl.utils.withLock
import org.kodein.db.leveldb.LevelDB
import org.kodein.memory.io.*
import org.kodein.memory.use

internal class DataDBImpl(override val ldb: LevelDB) : DataReadModule, DataDB {
    override val snapshot: LevelDB.Snapshot? get() = null

    internal val lock = newLock()

    companion object {
        internal const val DEFAULT_CAPACITY = 16384

        internal fun toLdb(options: Array<out Options.Write>): LevelDB.WriteOptions {
            val syncOption: DataWrite.Sync = options() ?: return LevelDB.WriteOptions.DEFAULT
            return LevelDB.WriteOptions(
                    sync = syncOption.sync
            )
        }
    }

    internal fun deleteIndexesInBatch(batch: LevelDB.WriteBatch, refKey: ReadBuffer) {
        val indexes = ldb.get(refKey) ?: return

        indexes.use {
            while (indexes.hasRemaining()) {
                val len = indexes.readInt()
                val indexKey = indexes.slice(indexes.position, len)
                batch.delete(indexKey)
                indexes.skip(len)
            }
        }

        batch.delete(refKey)
    }

    internal fun putIndexesInBatch(sb: SliceBuilder, batch: LevelDB.WriteBatch, key: ReadBuffer, refKey: ReadBuffer, indexes: Set<Index>) {
        if (indexes.isEmpty())
            return

        val ref = sb.newSlice {
            for (index in indexes) {
                val indexKeySize = getIndexKeySize(key, index.index, index.value)
                putInt(indexKeySize)
                val indexKey = subSlice { putIndexKey(key, index.index, index.value) }
                batch.put(indexKey, key)
            }
        }

        batch.put(refKey, ref)
    }

    private fun putInBatch(sb: SliceBuilder, batch: LevelDB.WriteBatch, key: ReadBuffer, body: Body, indexes: Set<Index>): Int {
        val refKey = sb.newSlice {
            putRefKeyFromObjectKey(key)
        }

        deleteIndexesInBatch(batch, refKey)
        putIndexesInBatch(sb, batch, key, refKey, indexes)

        val value = sb.newSlice { putBody(body) }
        batch.put(key, value)

        return value.remaining
    }

    private fun put(sb: SliceBuilder, key: ReadBuffer, body: Body, indexes: Set<Index>, vararg options: Options.Write): Int {
        val checks = options.all<Check>()
        ldb.newWriteBatch().use { batch ->
            lock.withLock {
                checks.forEach { it.block() }
                val length = putInBatch(sb, batch, key, body, indexes)
                ldb.write(batch, toLdb(options))
                return length
            }
        }
    }

    override fun put(key: ReadBuffer, body: Body, indexes: Set<Index>, vararg options: Options.Write): Int {
        key.verifyObjectKey()
        SliceBuilder.native(DEFAULT_CAPACITY).use {
            return put(it, key, body, indexes, *options)
        }
    }

    private fun deleteInBatch(sb: SliceBuilder, batch: LevelDB.WriteBatch, key: ReadBuffer) {
        val refKey = sb.newSlice { putRefKeyFromObjectKey(key) }

        deleteIndexesInBatch(batch, refKey)
        batch.delete(key)
    }

    override fun delete(key: ReadBuffer, vararg options: Options.Write) {
        key.verifyObjectKey()
        val checks = options.all<Check>()
        ldb.newWriteBatch().use { batch ->
            SliceBuilder.native(DEFAULT_CAPACITY).use {
                lock.withLock {
                    checks.forEach { it.block() }
                    deleteInBatch(it, batch, key)
                    ldb.write(batch, toLdb(options))
                }
            }
        }
    }

    override fun newBatch(): DataBatch = DataBatchImpl(this)

    override fun newSnapshot(vararg options: Options.Read): DataSnapshot = DataSnapshotImpl(ldb, ldb.newSnapshot())

    override fun close() {
        ldb.close()
    }
}

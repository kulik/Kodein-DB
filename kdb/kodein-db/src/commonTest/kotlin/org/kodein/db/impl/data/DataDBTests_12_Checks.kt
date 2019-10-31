package org.kodein.db.impl.data

import org.kodein.db.Value
import org.kodein.db.impl.Check
import org.kodein.memory.use
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

@Suppress("ClassName")
class DataDBTests_12_Checks : DataDBTests() {

    @Test
    fun test00_putOK() {
        val key = ddb.newHeapKey("int", Value.ofAscii("test"))

        ddb.put(key, Value.of(21))
        ddb.put(key, Value.of(42), emptySet(), Check { check(ddb.get(key)!!.readInt() == 21) })

        assertEquals(42, ddb.get(key)!!.readInt())
    }

    @Test
    fun test01_putKO() {
        val key = ddb.newHeapKey("int", Value.ofAscii("test"))

        ddb.put(key, Value.of(21))
        assertFailsWith<IllegalStateException> {
            ddb.put(key, Value.of(42), emptySet(), Check { check(ddb.get(key)!!.readInt() == 0) })
        }

        assertEquals(21, ddb.get(key)!!.readInt())
    }

    @Test
    fun test02_deleteOK() {
        val key = ddb.newHeapKey("int", Value.ofAscii("test"))
        ddb.put(key, Value.of(42))

        ddb.delete(key, Check { check(ddb.get(key)!!.readInt() == 42) })

        assertNull(ddb.get(key))
    }

    @Test
    fun test03_deleteKO() {
        val key = ddb.newHeapKey("int", Value.ofAscii("test"))
        ddb.put(key, Value.of(42))

        assertFailsWith<IllegalStateException> {
            ddb.delete(key, Check { check(ddb.get(ddb.newHeapKey("int", Value.ofAscii("test")))!!.readInt() == 0) })
        }

        assertEquals(42, ddb.get(key)!!.readInt())
    }

    @Test
    fun test04_batchOK() {
        val key = ddb.newHeapKey("int", Value.ofAscii("test"))
        ddb.put(key, Value.of(21))

        ddb.newBatch().use {
            it.put(key, Value.of(42))
            it.addWriteOptions(Check { check(ddb.get(key)!!.readInt() == 21) })
            it.write()
        }

        assertEquals(42, ddb.get(key)!!.readInt())
    }

    @Test
    fun test05_batchKO() {
        val key = ddb.newHeapKey("int", Value.ofAscii("test"))
        ddb.put(key, Value.of(21))

        ddb.newBatch().use {
            it.put(key, Value.of(42))
            it.addWriteOptions(Check { check(ddb.get(key)!!.readInt() == 0) })
            assertFailsWith<IllegalStateException> {
                it.write()
            }
        }

        assertEquals(21, ddb.get(key)!!.readInt())
    }

}

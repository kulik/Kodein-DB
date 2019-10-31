package org.kodein.db.impl.model

import org.kodein.db.model.findAllByIndex
import org.kodein.db.model.get
import org.kodein.memory.use
import kotlin.test.*

@Suppress("ClassName")
open class ModelDBTests_04_Refs : ModelDBTests() {

    @Test
    fun test00_Refs() {
        val me = Adult("Salomon", "BRYS", Date(15, 12, 1986))
        val laila = Adult("Laila", "BRYS", Date(25, 8, 1989))
        val meKey = mdb.newHeapKey(me)
        mdb.put(meKey, me)
        val lailaKey = mdb.newHeapKey(laila)
        mdb.put(lailaKey, laila)

        val sjeg = City("Saint Julien En Genevois", Location(46.1443, 6.0826), 74160)
        val pap = City("Pointe À Pitre", Location(16.2333, -61.5167), 97110)
        mdb.put(sjeg)
        mdb.put(pap)

        mdb.put(Birth(meKey, mdb.newHeapKey(sjeg)))
        mdb.put(Birth(lailaKey, mdb.newHeapKey((pap))))

        mdb.findAllByIndex<Birth>("date").use {
            assertTrue(it.isValid())
            val otherMe = mdb[it.model().value.adult]
            assertEquals(me, otherMe?.value)
            assertNotSame(me, otherMe?.value)
            val otherSjeg = mdb[it.model().value.city]
            assertEquals(sjeg, otherSjeg?.value)
            assertNotSame(sjeg, otherSjeg?.value)

            it.next()
            assertTrue(it.isValid())
            val otherLaila = mdb[it.model().value.adult]
            assertEquals(laila, otherLaila?.value)
            assertNotSame(laila, otherLaila?.value)
            val otherPap = mdb[it.model().value.city]
            assertEquals(pap, otherPap?.value)
            assertNotSame(pap, otherPap?.value)

            it.next()
            assertFalse(it.isValid())
        }
    }

}

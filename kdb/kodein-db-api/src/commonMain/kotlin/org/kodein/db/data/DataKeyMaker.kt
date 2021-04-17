package org.kodein.db.data

import org.kodein.db.Value
import org.kodein.memory.io.ReadMemory

public interface DataKeyMaker {

    public fun newKey(type: Int, id: Value): ReadMemory

}

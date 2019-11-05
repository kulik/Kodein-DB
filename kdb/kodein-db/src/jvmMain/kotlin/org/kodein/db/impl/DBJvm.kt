package org.kodein.db.impl

import org.kodein.db.DB
import org.kodein.db.DBFactory
import org.kodein.db.impl.model.ModelDBJvm
import org.kodein.db.model.ModelDB

object DBJvm : AbstractDBFactory() {
    override fun mdbFactory(): DBFactory<ModelDB> = ModelDBJvm
}

actual val DB.Companion.default: DBFactory<DB> get() = DBJvm
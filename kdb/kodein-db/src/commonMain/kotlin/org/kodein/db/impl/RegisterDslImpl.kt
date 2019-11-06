package org.kodein.db.impl

import org.kodein.db.DB
import org.kodein.db.DBListener
import org.kodein.db.Key
import org.kodein.db.Options
import org.kodein.db.model.ModelDB
import org.kodein.db.model.orm.Metadata
import org.kodein.memory.Closeable

internal class RegisterDslImpl<M : Any>(private val mdb: ModelDB, val filters: List<(M) -> Boolean>) : DB.RegisterDsl<M> {

    class FilteredListener<M : Any>(val listener: DBListener<M>, val filters: List<(M) -> Boolean>) : DBListener<M> {
        override fun willPut(model: M, typeName: String, metadata: Metadata, options: Array<out Options.Write>) {
            if (filters.all { it(model) }) listener.willPut(model, typeName, metadata, options)
        }

        override fun didPut(model: M, key: Key<*>, typeName: String, metadata: Metadata, size: Int, options: Array<out Options.Write>) {
            if (filters.all { it(model) }) listener.didPut(model, key, typeName, metadata, size, options)
        }

        override fun willDelete(key: Key<*>, getModel: () -> M?, typeName: String, options: Array<out Options.Write>) {
            if (filters.all { it(getModel() ?: return) }) listener.willDelete(key, getModel, typeName, options)
        }

        override fun didDelete(key: Key<*>, model: M?, typeName: String, options: Array<out Options.Write>) {
            if (filters.all { it(model ?: return) }) listener.didDelete(key, model, typeName, options)
        }
    }

    override fun filter(f: (M) -> Boolean): DB.RegisterDsl<M> = RegisterDslImpl(mdb, filters + f)

    private fun wrap(listener: DBListener<M>): DBListener<M> {
        if (filters.isEmpty())
            return listener
        return FilteredListener(listener, filters)
    }

    @Suppress("UNCHECKED_CAST")
    override fun register(listener: DBListener<M>): Closeable = mdb.register(wrap(listener) as DBListener<Any>)

    @Suppress("UNCHECKED_CAST")
    override fun register(builder: DBListener.Builder<M>.() -> Unit): Closeable = mdb.register(wrap(DBListener.Builder<M>().apply(builder).build()) as DBListener<Any>)

}
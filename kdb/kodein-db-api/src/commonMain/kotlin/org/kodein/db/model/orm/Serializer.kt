package org.kodein.db.model.orm

import org.kodein.db.Options
import org.kodein.db.Value
import org.kodein.memory.io.ReadBuffer
import org.kodein.memory.io.Writeable
import kotlin.reflect.KClass

interface Serializer<M : Any> {
    fun serialize(model: M, output: Writeable, vararg options: Options.Write)
    fun deserialize(type: KClass<out M>, transientId: ReadBuffer, input: ReadBuffer, vararg options: Options.Read): M

//    class ByClass(val default: Serializer<Any>? = null, build: Builder.() -> Unit) : Serializer<Any> {
//
//        val map = HashMap<KClass<*>, Serializer<*>>()
//
//        class Builder(@PublishedApi internal val map: MutableMap<KClass<*>, Serializer<*>>) {
//            inline operator fun <reified M : Any> Serializer<M>.unaryPlus() { map[M::class] =  this }
//        }
//
//        init { Builder(map).build() }
//
//        @Suppress("UNCHECKED_CAST")
//        override fun serialize(model: Any, output: Writeable, vararg options: Options.Write) =
//                (map[model::class] as? Serializer<Any>)?.serialize(model, output, *options)
//                        ?: default?.serialize(model, output, *options)
//                        ?: throw IllegalArgumentException("No serializer found for type ${model::class}")
//
//        @Suppress("UNCHECKED_CAST")
//        override fun <M : Any> deserialize(type: KClass<M>, input: ReadBuffer, vararg options: Options.Read): M =
//                (map[type] as? Serializer<Any>)?.deserialize(type, input, *options)
//                        ?: default?.deserialize(type, input, *options)
//                        ?: throw IllegalArgumentException("No serializer found for type $type")
//    }
}
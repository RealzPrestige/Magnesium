package dev.zprestige.magnesium.event.eventbus

import kotlin.reflect.KClass
import kotlin.reflect.full.*

open class EventBus {
    private val registeredClasses = HashMap<KClass<*>, List<EventListener>>()

    fun register(any: Any) {
        val c = any::class
        if (!registeredClasses.contains(c)) {
            registeredClasses[c] = listeners(any)
        }
    }


    fun unregister(any: Any) {
        val c = any::class
        if (!registeredClasses.contains(c)) {
            registeredClasses.remove(c)
        }
    }

    fun invoke(event: Event){
        registeredClasses.entries.forEach { entry ->
            if (!event.cancelled){
                entry.value
                    .filter { it.target == event::class }
                    .forEach {
                        it.invokeFunction(event)
                    }
            }
        }
    }

    private fun listeners(any: Any): List<EventListener> {
        val arrayList: ArrayList<EventListener> = ArrayList()
        val c = any::class
        c.declaredFunctions
            .filter { it.hasAnnotation<Listener>() }
            .mapTo(arrayList) { it.call(any) as EventListener }
        return arrayList
    }
}

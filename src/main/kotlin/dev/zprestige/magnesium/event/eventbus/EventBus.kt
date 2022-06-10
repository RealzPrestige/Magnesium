package dev.zprestige.magnesium.event.eventbus

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

open class EventBus(private val config: Config = Config()) {
    private val listeners = ConcurrentHashMap<KClass<*>, ListenerGroup>()
    private val subscribers = ConcurrentHashMap.newKeySet<Any>()
    private val cache = ConcurrentHashMap<Any, List<Listener>?>()

    fun subscribe(subscriber: Any): Boolean = subscribers.add(subscriber).also {
        if (it) cache.computeIfAbsent(subscriber) {
            getListeners(subscriber, config)
        }?.forEach(::register) ?: return false
    }

    fun unsubscribe(subscriber: Any): Boolean = subscribers.remove(subscriber).also {
        if (it) cache[subscriber]?.forEach(::unregister)
    }

    private fun register(listener: Listener): Boolean = listeners.computeIfAbsent(listener.type) {
        ListenerGroup(it, config)
    }.register(listener)

    private fun unregister(listener: Listener): Boolean = listeners[listener.type]?.let {
        val contained = it.unregister(listener)
        if (it.parallel.isEmpty() && it.sequential.isEmpty()) {
            listeners.remove(listener.type)
        }
        contained
    } ?: false

    fun post(event: Any): Boolean = listeners[event::class]?.post(event) ?: false

}


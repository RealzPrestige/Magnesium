package dev.zprestige.magnesium.event.eventbus

import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KClass

internal class ListenerGroup(
    private val type: KClass<*>
) {
    private val cancelledState = CancelledState(type)
    val sequential = CopyOnWriteArrayList<Listener>()
    val parallel = CopyOnWriteArrayList<Listener>()

    fun register(listener: Listener): Boolean = listWith(listener) {
        var position = it.size
        it.forEachIndexed { index, other ->
            if (listener == other) return false
            if (listener.priority > other.priority && position == it.size) {
                position = index
            }
        }
        it.add(position, listener)
        true
    }

    fun unregister(listener: Listener): Boolean = listWith(listener) {
        it.remove(listener)
    }

    fun post(event: Any): Boolean {
        sequential.forEach {
            if (!cancelledState.isCancelled(event)) {
                it.listener(event)
            }
        }
        val cancelled = cancelledState.isCancelled(event)
        if (parallel.isNotEmpty()) {
            parallel.forEach {
                if (!cancelled) {
                    it.listener(event)
                }
            }
        }
        return cancelled
    }

    private inline fun <R> listWith(listener: Listener, block: (MutableList<Listener>) -> R): R {
        return (if (listener.parallel) parallel else sequential).let {
            synchronized(it) {
                block(it)
            }
        }
    }

    override fun toString() = "${type.simpleName}: ${sequential.size}, ${parallel.size}"
}

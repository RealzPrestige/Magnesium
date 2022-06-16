package dev.zprestige.magnesium.event.eventbus

import java.util.function.Consumer
import kotlin.reflect.KClass


class Listener @PublishedApi internal constructor(
    listener: (Nothing) -> Unit,
    internal val type: KClass<*>,
    internal val priority: Int = 0,
    internal val parallel: Boolean = false
) {
    @Suppress("UNCHECKED_CAST")
    internal val listener = listener as (Any) -> Unit
    internal var subscriber: Any? = null
}

inline fun <reified T : Any> eventListener(
    priority: Int = 0,
    parallel: Boolean = false,
    noinline listener: (T) -> Unit
) = Listener(listener, T::class, priority, parallel)


@JvmOverloads
fun <T : Any> eventListener(
    type: Class<T>,
    priority: Int = 0,
    parallel: Boolean = false,
    listener: Consumer<T>
) = Listener(listener::accept, type.kotlin, priority, parallel)

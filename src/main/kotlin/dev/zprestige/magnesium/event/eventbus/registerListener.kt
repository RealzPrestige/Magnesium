package dev.zprestige.magnesium.event.eventbus

inline fun <reified T> registerListener(noinline function: (T) -> Unit) = EventListener(T::class, function)
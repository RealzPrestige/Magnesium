package dev.zprestige.magnesium.event.eventbus

import kotlin.reflect.KClass

class EventListener(val target: KClass<*>, function: (Nothing) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    internal val invokeFunction = function as (Any) -> Unit
}



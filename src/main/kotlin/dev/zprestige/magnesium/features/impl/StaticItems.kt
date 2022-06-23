package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.AnimateHeldItemsEvent
import dev.zprestige.magnesium.features.Feature

class StaticItems : Feature("Static Items", "Removes the animation on your items when you move your mouse") {

    @Listener
    fun onAnimateHeldItems() = registerListener<AnimateHeldItemsEvent> {
        it.cancelled = true
    }
}
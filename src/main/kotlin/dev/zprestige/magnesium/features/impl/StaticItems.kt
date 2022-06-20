package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.AnimateHeldItemsEvent
import dev.zprestige.magnesium.features.Feature

class StaticItems : Feature("Static Items", "Removes the animation on your items when you move your mouse") {

    @EventListener
    fun onAnimateHeldItems() = eventListener<AnimateHeldItemsEvent> {
        it.cancel()
    }
}
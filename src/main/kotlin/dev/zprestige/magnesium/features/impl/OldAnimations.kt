package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.HeldItemsEvent
import dev.zprestige.magnesium.features.Feature

class OldAnimations : Feature("Old Animations", "Mimics 1.8 animations") {

    @Listener
    fun onHeldItems() = registerListener<HeldItemsEvent> {
        it.cancelled = true
    }
}
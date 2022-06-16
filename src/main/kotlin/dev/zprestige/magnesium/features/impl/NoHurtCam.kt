package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.listener
import dev.zprestige.magnesium.event.impl.BobEvent
import dev.zprestige.magnesium.features.Feature

class NoHurtCam : Feature("NoHurtCam", "Removes the animation when damage taken") {

    @EventListener
    fun onBob() = listener<BobEvent> {
        it.cancelled = true
    }
}
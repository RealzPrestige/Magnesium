package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.BobEvent
import dev.zprestige.magnesium.features.Feature

class NoHurtCam : Feature("NoHurtCam", "Removes the animation when damage taken") {

    @Listener
    fun onBob() = registerListener<BobEvent> {
        it.cancelled = true
    }
}
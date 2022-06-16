package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.BobEvent
import dev.zprestige.magnesium.features.Feature

class NoHurtCam : Feature("NoHurtCam", "Removes the animation when damage taken") {

    @EventListener
    fun onBob() = eventListener<BobEvent> {
        it.cancel()
    }
}
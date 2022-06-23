package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.FireOverlayEvent
import dev.zprestige.magnesium.features.Feature

class LowFire : Feature("Low Fire", "Lowers the overlay of fire") {
    private val offset = inscribe("Offset", 0.0f, 0.0f, 0.5f)
    private val cancelOverlay = inscribe("Cancel Overlay", false)

    @Listener
    fun onFireOverlay() = registerListener<FireOverlayEvent> {
        it.offset -= offset.value
        if (cancelOverlay.value){
            it.cancelled = true
        }
    }
}
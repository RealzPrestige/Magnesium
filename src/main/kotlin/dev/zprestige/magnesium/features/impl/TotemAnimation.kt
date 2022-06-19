package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.FloatingItemEvent
import dev.zprestige.magnesium.features.Feature

class TotemAnimation : Feature("Totem Animation", "Changes the totem pop animation") {
    private val animationSpeed = inscribe("Animation Speed", 1, 0, 5)

    @EventListener
    fun onFloatingItem() = eventListener<FloatingItemEvent> {
        it.speed = animationSpeed.value
    }
}
package dev.zprestige.magnesium.features.impl


import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.FloatingItemEvent
import dev.zprestige.magnesium.features.Feature

class TotemAnimation : Feature("Totem Animation", "Changes the totem pop animation") {
    private val animationSpeed = inscribe("Animation Speed", 1, 0, 5)

    @Listener
    fun onFloatingItem() = registerListener<FloatingItemEvent> {
        it.speed = animationSpeed.value
    }
}
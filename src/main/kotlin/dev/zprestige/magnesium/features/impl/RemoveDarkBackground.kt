package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.BackgroundEvent
import dev.zprestige.magnesium.features.Feature

class RemoveDarkBackground : Feature("Remove Dark Background", "Removes the dark background behind guis") {

    @EventListener
    fun onBackground() = eventListener<BackgroundEvent> {
        it.cancel()
    }
}
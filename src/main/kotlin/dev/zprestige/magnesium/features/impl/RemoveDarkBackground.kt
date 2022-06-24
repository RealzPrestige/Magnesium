package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.BackgroundEvent
import dev.zprestige.magnesium.features.Feature

class RemoveDarkBackground : Feature("Remove Dark Background", "Removes the dark background behind guis") {

    @Listener
    fun onBackground() = registerListener<BackgroundEvent> {
        if (nullCheck()) {
            it.cancelled = true
        }
    }
}
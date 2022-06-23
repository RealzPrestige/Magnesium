package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.TickEvent
import dev.zprestige.magnesium.features.Feature

class FullBright : Feature("FullBright", "Sets brightness to the maximum to be able to see in the dark") {

    @Listener
    fun onTick() = registerListener<TickEvent> {
        if (nullCheck() && mc.options.gamma != 10000.0) {
            mc.options.gamma = 10000.0
        }
    }
}
package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.TerrainScreenEvent
import dev.zprestige.magnesium.features.Feature

class FastTerrainLoading : Feature("Fast Terrain Loading", "Loads terrain (very fast)") {

    @Listener
    fun onTerrainScreen() = registerListener<TerrainScreenEvent> {
        it.cancelled = true
    }
}
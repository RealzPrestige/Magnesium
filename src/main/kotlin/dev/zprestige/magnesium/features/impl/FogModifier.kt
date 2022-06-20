package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.RenderTerrainFogEvent
import dev.zprestige.magnesium.features.Feature

class FogModifier : Feature("Fog Modifier", "Modifies the fog in the world") {
    private val startDistance = inscribe("Start Distance", 50.0f, 0.1f, 250.0f)
    private val endDistance = inscribe("End Distance", 50.0f, 0.1f, 250.0f)

    @EventListener
    fun onRenderTerrainFog() = eventListener<RenderTerrainFogEvent> {
        it.cancel()
        it.start = startDistance.value
        it.end  = endDistance.value
    }
}
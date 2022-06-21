package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.ColorFogEvent
import dev.zprestige.magnesium.event.impl.RenderTerrainFogEvent
import dev.zprestige.magnesium.features.Feature
import java.awt.Color

class FogModifier : Feature("Fog Modifier", "Modifies the fog in the world") {
    private val startDistance = inscribe("Start Distance", 50.0f, 0.1f, 250.0f)
    private val endDistance = inscribe("End Distance", 50.0f, 0.1f, 250.0f)
    private val fogColor = inscribe("Fog Color", Color.WHITE)

    @EventListener
    fun onRenderTerrainFog() = eventListener<RenderTerrainFogEvent> {
        it.cancel()
        it.start = startDistance.value
        it.end  = endDistance.value
    }

    @EventListener
    fun onColorFog() = eventListener<ColorFogEvent> {
        it.red = fogColor.value.red / 255.0f
        it.green = fogColor.value.green / 255.0f
        it.blue = fogColor.value.blue / 255.0f
    }
}
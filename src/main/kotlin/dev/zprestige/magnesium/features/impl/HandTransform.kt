package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.RenderHandEvent
import dev.zprestige.magnesium.features.Feature

class HandTransform : Feature("Hand Transform", "Transforms the look of your items") {
    private val x = inscribe("x", 0.0f, -2.0f, 2.0f)
    private val y = inscribe("y", 0.0f, -2.0f, 2.0f)
    private val z = inscribe("z", 0.0f, -2.0f, 2.0f)
    private val scaleX = inscribe("Scale x", 1.0f, 0.1f, 2.0f)
    private val scaleY = inscribe("Scale y", 1.0f, 0.1f, 2.0f)
    private val scaleZ = inscribe("Scale z", 1.0f, 0.1f, 2.0f)


    @EventListener
    fun onRenderHand() = eventListener<RenderHandEvent> {
        it.cancel()
        it.x = x.value
        it.y = y.value
        it.z = z.value
        it.scaleX = scaleX.value
        it.scaleY = scaleY.value
        it.scaleZ = scaleZ.value
    }
}
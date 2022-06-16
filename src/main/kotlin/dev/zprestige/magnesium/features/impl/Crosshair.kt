package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.CrosshairEvent
import dev.zprestige.magnesium.event.impl.Render2DEvent
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.util.RenderUtil
import java.awt.Color

class Crosshair : Feature("Crosshair", "Changes the look of your crosshair") {
    private val length = inscribe("Length", 1.0f, 0.1f, 5.0f)
    private val thickness = inscribe("Thickness", 1.0f, 0.1f, 1.2f)
    private val gap = inscribe("Gap", 1.0f, 0.1f, 5.0f)
    private val color = inscribe("Color", Color.WHITE)

    @EventListener
    fun onCrosshair() = eventListener<CrosshairEvent> {
        it.cancel()
    }

    @EventListener
    fun onRender2D() = eventListener<Render2DEvent> {
        val matrices = it.matrixStack
        val centerX = it.scaledWidth / 2.0f
        val centerY = it.scaledHeight / 2.0f
        val gap = this.gap.value
        val length = this.length.value
        val thickness = this.thickness.value
        val color = this.color.value
        RenderUtil.fillCorrectly(matrices, centerX - gap - length, centerY - thickness, centerX - gap, centerY + thickness, color)
        RenderUtil.fillCorrectly(matrices, centerX + gap, centerY - thickness, centerX + gap + length, centerY + thickness, color)
        RenderUtil.fillCorrectly(matrices, centerX - thickness, centerY - gap - length, centerX + thickness, centerY - gap, color)
        RenderUtil.fillCorrectly(matrices, centerX - thickness, centerY + gap, centerX + thickness, centerY + gap + length, color)
    }
}
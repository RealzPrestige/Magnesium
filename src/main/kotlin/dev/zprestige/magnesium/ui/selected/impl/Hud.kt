package dev.zprestige.magnesium.ui.selected.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.ui.ClickGui
import dev.zprestige.magnesium.ui.button.Button
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.awt.Color

class Hud : Screen(Text.of("Hud")) {
    private val back = Button("Back", { Main.mc.setScreen(ClickGui()) }, false)
    private var clickFrame = false
    private var releaseFrame = false
    private var holding = false
    private val hudDrawables = ArrayList<HudDrawable>()

    init {
        Main.featureManager.features
            .filter { it.hudComponent != null && it.enabled.value }
            .mapTo(hudDrawables) { HudDrawable(it) }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        back.setup(width / 2.0f - 50.5f, height / 2.0f - 7.5f, 100.0f, 15.0f)
        back.render(matrices, mouseX, mouseY, clickFrame)
        hudDrawables.forEach {
            it.render(matrices, mouseX, mouseY)
        }
        if (clickFrame) {
            holding = true
        }
        if (releaseFrame) {
            holding = false
        }
        clickFrame = false
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
            hudDrawables.forEach { it.mouseClicked(mouseX, mouseY) }
        clickFrame = true
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        hudDrawables.forEach { it.mouseReleased() }
        releaseFrame = true
        return super.mouseReleased(mouseX, mouseY, button)
    }

    inner class HudDrawable(feature: Feature) {
        private val hudComponent: Feature.HudComponent? = feature.hudComponent
        private var dragX = 0.0f
        private var dragY = 0.0f
        private var dragging = false

        fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
            drag(mouseX, mouseY)
            RenderUtil.outline(matrices,
                hudComponent!!.x,
                hudComponent.y,
                hudComponent.x + hudComponent.width,
                hudComponent.y + hudComponent.height,
                1.0f,
                Color.WHITE
            )
        }

        private fun drag(mouseX: Int, mouseY: Int) {
            if (dragging){
                hudComponent!!.x = dragX + mouseX
                hudComponent.y = dragY + mouseY
            }
        }

        fun mouseClicked(mouseX: Double, mouseY: Double) {
            if (hovering(mouseX, mouseY)) {
                dragX = (hudComponent!!.x - mouseX).toFloat()
                dragY = (hudComponent.y - mouseY).toFloat()
                dragging = true
            }
        }

        fun mouseReleased() {
            dragging = false
        }

        fun hovering(mouseX: Double, mouseY: Double): Boolean {
            return mouseX > hudComponent!!.x && mouseY > hudComponent.y && mouseX < hudComponent.x + hudComponent.width && mouseY < hudComponent.y + hudComponent.height
        }

    }
}
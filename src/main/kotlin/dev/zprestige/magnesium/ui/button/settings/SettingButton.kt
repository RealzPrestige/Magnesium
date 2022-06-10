package dev.zprestige.magnesium.ui.button.settings

import dev.zprestige.magnesium.features.Feature
import net.minecraft.client.util.math.MatrixStack

class SettingButton(val feature: Feature, val x: Float, var y: Float, val width: Float, val height: Float) {

    fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {

    }

    fun hovering(mouseX: Float, mouseY: Float): Boolean {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height
    }
}
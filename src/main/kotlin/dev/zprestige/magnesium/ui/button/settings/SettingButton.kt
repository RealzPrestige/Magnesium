package dev.zprestige.magnesium.ui.button.settings

import dev.zprestige.magnesium.settings.Setting
import net.minecraft.client.util.math.MatrixStack

open class SettingButton(var setting: Setting<*>, var x: Float, var y: Float, var width: Float, var height: Float) {

    open fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {}

    open fun keyPressed(keyCode: Int) {}

    open fun charTyped(chr: Char) {}

    open fun hovering(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height
    }

    open fun setup(x: Float, y: Float, width: Float, height: Float){
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    fun convertWhiteToGray(input: Int): Int {
        return if (input > 150) {
            input - 2
        } else input
    }

    fun convertGrayToWhite(input: Int): Int {
        return if (input < 254) {
            input + 2
        } else input
    }

    fun tab(): String {
        return setting.tab
    }
}
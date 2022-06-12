package dev.zprestige.magnesium.ui.button.settings.impl.feature

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.impl.Keybind
import dev.zprestige.magnesium.ui.button.settings.SettingButton
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

class FeatureKeybind(private val keybind: Keybind, x: Float, y: Float, width: Float, height: Float) : SettingButton(keybind, x,
    y,
    width,
    height
) {

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        RenderUtil.fill(matrices, x, y, x + width, y + height, Color(0, 0, 0, 30))
        RenderUtil.fillGradient(matrices,
            x + width - 0.25f,
            y - 0.25f,
            x + width + 4,
            y + height - 0.25f,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 0),
            true
        )
        RenderUtil.fillGradient(matrices,
            x - 4.0f,
            y - 0.25f,
            x - 0.25f,
            y + height - 0.25f,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 30),
            true
        )
        var text: String = keybind.value.toChar().toString()
        if (keybind.value == -1) {
            text = "NONE"
        }
        Main.fontManager.prepare(0.8f).drawStringWithShadow(matrices,
            text,
            (x + (width / 2.0f)) - (Main.fontManager.getStringWidth(text, 0.9f) / 2.0f),
            (y + (height / 2.0f)) - (Main.fontManager.getHeight(0.8f) / 2.0f),
            Color.WHITE
        ).pop()
    }
}
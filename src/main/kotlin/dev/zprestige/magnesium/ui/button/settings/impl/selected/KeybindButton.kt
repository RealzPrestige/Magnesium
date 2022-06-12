package dev.zprestige.magnesium.ui.button.settings.impl.selected

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.impl.Keybind
import dev.zprestige.magnesium.ui.button.settings.SettingButton
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

class KeybindButton(private val keybind: Keybind, x: Float, y: Float, width: Float, height: Float) : SettingButton(
    keybind,
    x,
    y,
    width,
    height
) {
    private var prevTime = 0L
    private var typing = false
    var color: Color = Color(150, 150, 150, 255)


    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        Main.fontManager.drawStringWithShadow(matrices,
            keybind.name,
            x,
            y + (height / 2.0f) - (Main.fontManager.getHeight() / 2.0f),
            Color.WHITE
        )
        RenderUtil.fill(matrices, x + width - 104, y + 1, x + width - 4, y + height - 1, Color(0, 0, 0, 30))
        RenderUtil.fillGradient(matrices,
            x + width - 4,
            y + 1,
            x + width,
            y + height - 1,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 0),
            true
        )
        RenderUtil.fillGradient(matrices,
            x + width - 108,
            y + 1,
            x + width - 104,
            y + height - 1,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 30),
            true
        )
        color = if (hovering(mouseX, mouseY)) {
            Color(convertGrayToWhite(color.red), convertGrayToWhite(color.green), convertGrayToWhite(color.blue))
        } else {
            Color(convertWhiteToGray(color.red), convertWhiteToGray(color.green), convertWhiteToGray(color.blue))
        }
        var text: String = keybind.value.toChar().toString()
        if (keybind.value == -1) {
            text = "NONE"
        }
        if (typing){
            text = typingIcon()
        }
        Main.fontManager.drawStringWithShadow(matrices, text, x + width - 54.0f - (Main.fontManager.getStringWidth(text) / 2.0f), ((y + 1) + ((height - 1) / 2.0f)) - (Main.fontManager.getHeight() / 2.0f), color)
        if (hovering(mouseX, mouseY) && clickFrame){
            typing = !typing
        }
    }

    override fun charTyped(chr: Char) {
        if (typing){
            if (chr.code != 167 && chr >= ' ' && chr.code != 127) {
                keybind.value = chr.uppercase().toCharArray()[0].code
                typing = false
            }
        }
    }

    override fun hovering(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x + width - 104 && mouseY > y + 1 && mouseX < x + width - 4 && mouseY < y + height - 1
    }

    private fun typingIcon(): String {
        val time = System.currentTimeMillis()
        if (time - prevTime <= 500 && typing) {
            return "_"
        }
        if (time - prevTime >= 1000) {
            prevTime = time
        }
        return ""
    }
}
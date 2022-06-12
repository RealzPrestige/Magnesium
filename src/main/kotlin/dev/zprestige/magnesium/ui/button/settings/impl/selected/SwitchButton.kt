package dev.zprestige.magnesium.ui.button.settings.impl.selected

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.impl.Switch
import dev.zprestige.magnesium.ui.button.settings.SettingButton
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

class SwitchButton(private val switch: Switch, x: Float, y: Float, width: Float, height: Float) : SettingButton(switch,
    x,
    y,
    width,
    height
) {

    private var animationValue: Float = if (switch.value) {
        5.0f
    } else {
        15.0f
    }
    var color: Color = if (switch.value) {
        Color.WHITE
    } else {
        Color(150, 150, 150, 255)
    }
    private var hoverColor: Color = if (switch.value) {
        Color.WHITE
    } else {
        Color(150, 150, 150, 255)
    }


    init {
        animationValue = if (switch.value) {
            5.0f
        } else {
            15.0f
        }
        color = if (switch.value) {
            Color.WHITE
        } else {
            Color(150, 150, 150, 255)
        }
        hoverColor = if (switch.value) {
            Color.WHITE
        } else {
            Color(150, 150, 150, 255)
        }
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        RenderUtil.fill(matrices, x, y, x + 15.0f, y + height, Color(0, 0, 0, 30))
        RenderUtil.fillGradient(matrices,
            x + 15.0f - 0.5f,
            y - 0.25f,
            x + 15.0f + 4,
            y + height - 0.25f,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 0),
            true
        )
        RenderUtil.fillGradient(matrices,
            x - 4.0f,
            y - 0.25f,
            x - 0.5f,
            y + height - 0.25f,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 30),
            true
        )
        Main.fontManager.drawStringWithShadow(matrices,
            switch.name,
            x + 20.0f,
            y + (height / 2.0f) - (Main.fontManager.getHeight() / 2.0f),
            color
        )

        color = if (hovering(mouseX, mouseY) || switch.value) {
            Color(convertGrayToWhite(color.red), convertGrayToWhite(color.green), convertGrayToWhite(color.blue))
        } else {
            Color(convertWhiteToGray(color.red), convertWhiteToGray(color.green), convertWhiteToGray(color.blue))
        }
        if (hovering(mouseX, mouseY) && clickFrame) {
            switch.value = !switch.value
        }
        if (switch.value || hovering(mouseX, mouseY)) {
            if (animationValue > 5.0f) {
                animationValue -= animationValue / 20.0f
                if (animationValue - 1 <= 5.0f) {
                    animationValue = 5.0f
                }
            }
            if (hovering(mouseX, mouseY) && !switch.value) {
                hoverColor = Color(convertWhiteToGray(hoverColor.red),
                    convertWhiteToGray(hoverColor.green),
                    convertWhiteToGray(hoverColor.blue)
                )
            }
            if (switch.value) {
                hoverColor = Color(convertGrayToWhite(hoverColor.red),
                    convertGrayToWhite(hoverColor.green),
                    convertGrayToWhite(hoverColor.blue)
                )
            }
        } else {
            if (animationValue < 15.0f) {
                animationValue += (15.0f - animationValue) / 10.0f
                if (animationValue + 1 >= 15.0f) {
                    animationValue = 15.0f
                }
            }
            hoverColor = Color(convertWhiteToGray(hoverColor.red),
                convertWhiteToGray(hoverColor.green),
                convertWhiteToGray(hoverColor.blue)
            )
        }
        if (animationValue < 15.0f) {
            RenderUtil.fillGradient(matrices,
                x + (animationValue / 2.0f) - 0.5f,
                y + (animationValue / 2.0f) - 0.5f,
                x + 15.0f - (animationValue / 2.0f) + 0.5f,
                y + height - (animationValue / 2.0f) + 0.5f,
                Color(0, 0, 0, 100),
                Color(0, 0, 0, 100),
                true
            )
            RenderUtil.fillGradient(matrices,
                x + (animationValue / 2.0f),
                y + (animationValue / 2.0f),
                x + 15.0f - (animationValue / 2.0f),
                y + height - (animationValue / 2.0f),
                hoverColor,
                hoverColor,
                true
            )
        }
    }

    override fun hovering(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseX < x + 15.0f && mouseY > y && mouseY < y + height
    }
}
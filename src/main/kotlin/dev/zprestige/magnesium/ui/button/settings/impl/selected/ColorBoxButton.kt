package dev.zprestige.magnesium.ui.button.settings.impl.selected

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.impl.ColorBox
import dev.zprestige.magnesium.ui.button.settings.SettingButton
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import java.awt.Color
import kotlin.math.max

class ColorBoxButton(private val colorBox: ColorBox, x: Float, y: Float, width: Float, height: Float) : SettingButton(
    colorBox,
    x,
    y,
    width,
    height
) {
    var opened = false
    private var holding = false
    private var hue = 0.0f
    private var pickerX = 0.0f
    private var pickerY = 0.0f
    private var color = Color(0, 0, 0, 0)
    private var pickingColor = Color(0, 0, 0, 0)
    private var alphaColor = Color(0, 0, 0, 0)

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        if (opened) {
            RenderUtil.fillGradient(matrices,
                x + 2.5f,
                y + height - 1.0f,
                x + 183.0f,
                y + height + 51.0f,
                Color(0, 0, 0, 50),
                Color(0, 0, 0, 50),
                true
            )
        }
        RenderUtil.fillGradient(matrices,
            x + 3.5f,
            y + 3.5f,
            x + 11.5f,
            y + height - 3.5f,
            Color(0, 0, 0, 100),
            Color(0, 0, 0, 100),
            true
        )
        RenderUtil.fillGradient(matrices,
            x + 4,
            y + 4,
            x + 11.0f,
            y + height - 4.0f,
            colorBox.value,
            colorBox.value,
            true
        )
        Main.fontManager.drawStringWithShadow(matrices,
            colorBox.name,
            x + 17.0f,
            y + (height / 2.0f) - (Main.fontManager.getHeight() / 2.0f),
            Color.WHITE
        )
        if (clickFrame) {
            holding = true
        }
        if (releaseFrame) {
            holding = false
        }
        if (opened) {
            updateColor(mouseX, mouseY)
            RenderUtil.fillGradient(matrices,
                x + 4.0f,
                y + height,
                x + 154.0f,
                y + height + 50.0f,
                Color.WHITE,
                Color(colorBox.value.red, colorBox.value.green, colorBox.value.blue),
                Color.BLACK,
                Color.BLACK
            )
            drawAlphaSlider(matrices)
            drawHueGradient(matrices)
            RenderUtil.fillGradient(matrices,
                pickerX - 1.5f,
                pickerY - 1.5f,
                pickerX + 1.5f,
                pickerY + 1.5f,
                pickingColor,
                pickingColor,
                true
            )
        }
        if (hovering(mouseX, mouseY) && clickFrame) {
            opened = !opened
        }

    }

    override fun hovering(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x + 3.5f && mouseX < x + 11.5f && mouseY > y + 3.5f && mouseY < y + height - 3.5f
    }

    private fun updateColor(mouseX: Int, mouseY: Int) {
        val hsb = Color.RGBtoHSB(colorBox.value.red, colorBox.value.green, colorBox.value.blue, null)
        val prevAlpha = colorBox.value.alpha
        if (holding) {
            if (mouseX > x + 4.0f && mouseX < x + 154.0f && mouseY > y + height && mouseY < y + height + 50.0f) {
                val mX = (mouseX - (x + 4.0f)).toInt()
                var saturation: Float = mX / 150.0f
                saturation = MathHelper.clamp(saturation, 0f, 0.99f)
                pickerX = x + 4.0f + (150.0f * saturation)
                hsb[1] = saturation
                val mY = (mouseY - (y + height)).toInt()
                var brightness = mY / 50.0f
                brightness = MathHelper.clamp(brightness, 0f, 0.99f)
                pickerY = y + height + 50.0f * brightness
                hsb[2] = 1.0f - brightness
                colorBox.value = Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]))
                colorBox.value = Color(colorBox.value.red, colorBox.value.green, colorBox.value.blue, prevAlpha)
                if (pickingColor.alpha < 150.0f) {
                    pickingColor = Color(pickingColor.red,
                        pickingColor.blue,
                        pickingColor.green,
                        150.coerceAtMost(pickingColor.alpha + 4)
                    )
                }
            }
            if (mouseX > x + 158.0f && mouseX < x + 168.0f && mouseY > y + height && mouseY < y + height + 50.0f) {
                val mY = (mouseY - (y + height)).toInt()
                var hue = mY / 50.0f
                hue = MathHelper.clamp(hue, 0.0f, 0.99f)
                this.hue = hue
                hsb[0] = hue
                colorBox.value = Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]))
                colorBox.value = Color(colorBox.value.red, colorBox.value.green, colorBox.value.blue, prevAlpha)
                if (color.alpha < 150.0f) {
                    color = Color(color.red, color.blue, color.green, 150.coerceAtMost(color.alpha + 4))
                }
            }
            if (mouseX > x + 172.0f && mouseX < x + 182.0f && mouseY > y + height - 5.0f && mouseY < y + height + 50.0f) {
                val mY = (mouseY - (y + height)).toInt()
                var alpha = mY / 50.0f
                alpha = MathHelper.clamp(alpha, 0.0f, 0.99f)
                colorBox.value = Color(colorBox.value.red / 255.0f,
                    colorBox.value.green / 255.0f,
                    colorBox.value.blue / 255.0f,
                    alpha
                )
                if (alphaColor.alpha < 150.0f) {
                    alphaColor =
                        Color(alphaColor.red, alphaColor.blue, alphaColor.green, 150.coerceAtMost(alphaColor.alpha + 4))
                }
            }
        } else {
            if (color.alpha > 0.0f) {
                color = Color(color.red, color.blue, color.green, max(0, color.alpha - 4))
            }
            if (pickingColor.alpha > 0.0f) {
                pickingColor =
                    Color(pickingColor.red, pickingColor.blue, pickingColor.green, max(0, pickingColor.alpha - 4))
            }
            if (alphaColor.alpha > 0.0f) {
                alphaColor = Color(alphaColor.red, alphaColor.blue, alphaColor.green, max(0, alphaColor.alpha - 4))
            }
        }
    }

    private fun drawAlphaSlider(matrices: MatrixStack) {
        val rightX = x + 177.0f
        val leftX = x + 172.0f
        var deltaY = y + height
        for (k in 0..4) {
            var right = false
            var gray = false
            var rightGray = 0
            for (i in 0..3) {
                val posX = if (right) {
                    rightX
                } else {
                    leftX
                }
                val color = if (gray) {
                    Color.GRAY
                } else {
                    Color.WHITE
                }
                RenderUtil.fillGradient(matrices,
                    posX,
                    deltaY,
                    posX + 5.0f,
                    deltaY + 5.0f,
                    color,
                    color,
                    true
                )
                if (right) {
                    deltaY += 5.0f
                }
                if (!gray) {
                    gray = true
                } else {
                    if (rightGray == 1) {
                        gray = false
                        rightGray = 0
                    } else {
                        rightGray += 1
                    }
                }
                right = !right
            }
        }
        RenderUtil.fillGradient(matrices,
            x + 172.0f,
            y + height,
            x + 182.0f,
            y + height + 50.0f,
            Color(0, 0, 0, 0),
            Color(0, 0, 0),
            false
        )
        val sliderY = (50.0f * (colorBox.value.alpha / 255.0f))
        RenderUtil.fillGradient(matrices,
            x + 172.0f,
            y + height + sliderY - 0.5f,
            x + 182.0f,
            y + height + sliderY + 0.5f,
            alphaColor,
            alphaColor,
            false
        )
    }

    private fun drawHueGradient(matrices: MatrixStack) {
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height,
            x + 168.0f,
            y + height + 5.0f,
            Color(255, 0, 0),
            Color(255, 127, 0),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 5.0f,
            x + 168.0f,
            y + height + 10.0f,
            Color(255, 127, 0),
            Color(255, 255, 0),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 10.0f,
            x + 168.0f,
            y + height + 15.0f,
            Color(255, 255, 0),
            Color(127, 255, 0),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 15.0f,
            x + 168.0f,
            y + height + 20.0f,
            Color(127, 255, 0),
            Color(0, 255, 0),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 20.0f,
            x + 168.0f,
            y + height + 25.0f,
            Color(0, 255, 0),
            Color(0, 255, 127),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 25.0f,
            x + 168.0f,
            y + height + 30.0f,
            Color(0, 255, 127),
            Color(0, 255, 255),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 30.0f,
            x + 168.0f,
            y + height + 35.0f,
            Color(0, 255, 255),
            Color(0, 127, 255),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 35.0f,
            x + 168.0f,
            y + height + 40.0f,
            Color(0, 127, 255),
            Color(0, 0, 255),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 40.0f,
            x + 168.0f,
            y + height + 45.0f,
            Color(0, 0, 255),
            Color(127, 0, 255),
            false
        )
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            y + height + 45.0f,
            x + 168.0f,
            y + height + 50.0f,
            Color(127, 0, 255),
            Color(255, 0, 255),
            false
        )
        val hueSliderHeight = y + height + (50.0f * hue)
        RenderUtil.fillGradient(matrices,
            x + 158.0f,
            hueSliderHeight - 0.5f,
            x + 168.0f,
            hueSliderHeight + 0.5f,
            color,
            color,
            true
        )
    }
}
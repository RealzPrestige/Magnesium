package dev.zprestige.magnesium.ui.button.settings.impl.selected

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.impl.SliderInt
import dev.zprestige.magnesium.ui.button.settings.SettingButton
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.max
import kotlin.math.min

class SliderIntButton(private val slider: SliderInt, x: Float, y: Float, width: Float, height: Float) : SettingButton(
    slider,
    x,
    y,
    width,
    height
) {
    var targetPos = 0.0f
    var pos = 0.0f
    var holding = false

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        if (hovering(mouseX, mouseY) && holding) {
            updateValue(mouseX)
        }
        Main.fontManager.drawStringWithShadow(matrices,
            slider.name,
            x,
            y + (height / 2.0f) - (Main.fontManager.getHeight() / 2.0f),
            Color.WHITE
        )
        RenderUtil.fillGradient(matrices,
            x + width / 2.0f,
            y + height / 2.0f - 1f,
            x + width - 4,
            y + height / 2.0f + 1f,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 30),
            true
        )
        RenderUtil.fillGradient(matrices,
            x + width / 2.0f - 1,
            y + (height / 3.0f),
            x + width / 2.0f,
            y + height - (height / 3.0f),
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 30),
            true
        )
        RenderUtil.fillGradient(matrices,
            x + width - 4,
            y + (height / 3.0f),
            x + width - 3,
            y + height - (height / 3.0f),
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 30),
            true
        )
        targetPos = width / 2.0f + ((((width - 4) - (width / 2.0f))) * sliderWidthValue())
        if (pos == 0.0f){
            pos = targetPos
        } else {
            if (pos < targetPos){
                if (pos + 1.0f >= targetPos){
                    pos = targetPos
                } else {
                    pos += 2.0f
                }
            } else if (pos > targetPos){
                if (pos - 1.0f <= targetPos){
                    pos = targetPos
                } else {
                    pos -= 2.0f
                }
            }
        }
        RenderUtil.fillGradient(matrices,
            x + pos - 0.5f,
            y + (height / 3.0f),
            x + pos + 0.5f,
            y + height - (height / 3.0f),
            Color(255, 255, 255, 150),
            Color(255, 255, 255, 150),
            true
        )
        Main.fontManager.prepare(0.5f).drawStringWithShadow(matrices,
            slider.min.toString(),
            x + width / 2.0f - 0.5f - (Main.fontManager.getStringWidth(slider.min.toString(), 0.5f) / 2.0f),
            y + height - (height / 3.0f),
            Color.WHITE
        ).drawStringWithShadow(matrices,
            slider.max.toString(),
            x + width - 3.5f - (Main.fontManager.getStringWidth(slider.max.toString(), 0.5f) / 2.0f),
            y + height - (height / 3.0f),
            Color.WHITE
        ).drawStringWithShadow(matrices,
            slider.value.toString(),
            x + pos - (Main.fontManager.getStringWidth(slider.value.toString(), 0.5f) / 2.0f),
            y + (height / 3.0f) - Main.fontManager.getHeight(0.5f),
            Color.WHITE
        ).pop()
        if (clickFrame){
            holding = true
        }
        if (releaseFrame){
            holding = false
        }
    }

    override fun hovering(mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x + width / 2.0f - 5 && mouseX < x + width + 1 && mouseY > y  + (height / 3.0f) && mouseY < y + height - (height / 3.0f)
    }

    private fun updateValue(mouseX: Int) {
        slider.value = slider.min
        val w = (width - 4) - (width / 2.0f)
        val diff = min(w, max(0.0f, mouseX - (x +  width / 2.0f)))
        val min: Int = slider.min
        val max: Int = slider.max
        val value = roundNumber((diff / w * (max - min) + min).toDouble())
        slider.value = if (diff == 0f) {
            slider.min
        } else {
            value.coerceAtMost(slider.max.toFloat()).toInt()
        }
    }

    private fun roundNumber(value: Double): Float {
        var decimal = BigDecimal.valueOf(value)
        decimal = decimal.setScale(1, RoundingMode.FLOOR)
        return decimal.toFloat()
    }

    private fun sliderWidthValue(): Float {
        return (slider.value - slider.min).toFloat() / (slider.max - slider.min).toFloat()
    }
}
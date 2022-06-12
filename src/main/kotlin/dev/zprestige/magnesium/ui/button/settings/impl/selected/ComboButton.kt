package dev.zprestige.magnesium.ui.button.settings.impl.selected

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.impl.Combo
import dev.zprestige.magnesium.ui.button.settings.SettingButton
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import java.awt.Color
import java.util.stream.IntStream

class ComboButton(private val combo: Combo, x: Float, y: Float, width: Float, height: Float) : SettingButton(
    combo,
    x,
    y,
    width,
    height
) {
    private val rightArrow: Identifier = Identifier("magnesium", "icons/arrow-right.png")
    private val leftArrow: Identifier = Identifier("magnesium", "icons/arrow-left.png")

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        Main.fontManager.drawStringWithShadow(matrices,
            combo.name,
            x,
            y + (height / 2.0f) - (Main.fontManager.getHeight() / 2.0f),
            Color.WHITE
        )
        Main.fontManager.drawStringWithShadow(matrices,
            combo.value,
            x + width - 64 - (Main.fontManager.getStringWidth(combo.value) / 2.0f),
            y + (height / 2.0f) - (Main.fontManager.getHeight() / 2.0f),
            Color.WHITE
        )
        RenderUtil.drawTexture(matrices, leftArrow, (x + width - 119).toInt(), y.toInt(), 0, 0.0f, 0.0f, 10, 15, 15, 15)
        RenderUtil.drawTexture(matrices, rightArrow, (x + width - 19).toInt(), y.toInt(), 0, 5.0f, 0.0f, 10, 15, 15, 15)
        val insideHeight = mouseY > y && mouseY < y + height
        val insideLeft = mouseX > x + width - 119 && mouseX < x + width - 109
        val insideRight = mouseX > x + width - 19 && mouseX < x + width - 4
        val max = combo.modes.size
        val index = IntStream.range(0, combo.modes.size).filter { i: Int ->
            combo.modes[i] == combo.value
        }.findFirst().orElse(-1)
        if (clickFrame && insideHeight) {
            if (insideRight) {
                combo.value = combo.modes[if (index + 1 >= max) 0 else index + 1]
            }
            if (insideLeft){
                combo.value = combo.modes[if (index - 1 < 0) max - 1 else index - 1]
            }
        }
        var startX = 0.0f
        repeat(combo.modes.count()) {
            startX += 5.0f
        }
        var deltaX = (x + width - 64 )- (startX * 1.5f)
        for (mode in combo.modes){
            RenderUtil.fillGradient(matrices, startX + deltaX - 0.5f, y + height - 1.5f, startX + deltaX + 3.5f, y + height + 0.5f, Color(0, 0, 0, 50), Color(0, 0, 0, 50), true)
            val col = if (combo.value == mode){
                Color.WHITE
            } else {
                Color.GRAY
            }
            RenderUtil.fillGradient(matrices, startX + deltaX, y + height - 1.0f, startX + deltaX + 3.0f, y + height, col, col, true)
             deltaX += 5.0f
        }
    }
}
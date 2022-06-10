package dev.zprestige.magnesium.ui.button

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

class Button(private val text: String, private val runnable: Runnable) {
    var x: Float = 0.0f
    var y: Float = 0.0f
    var width: Float = 0.0f
    var height: Float = 0.0f
    var scale = 1.0f

    fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {
        /** checks if inside bounding box */
        val hovering = mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height

        /** Increase scale if hovering */
        if (hovering) {
            if (clickFrame) {
                runnable.run()
            }
            if (scale < 1.05f) {
                scale += 0.01f
            }
        } else {
            if (scale > 1.0f) {
                scale -= 0.01f
            }
        }


        /** render box */
        RenderUtil.fill(matrices, x, y, x + width, y + height, Color(0, 0, 0, 30))

        /** gradients on side of box */
        RenderUtil.fillGradient(matrices,
            x + width - 0.5f,
            y - 0.5f,
            x + width + 4,
            y + height - 0.5f,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 0),
            true
        )
        RenderUtil.fillGradient(matrices,
            x - 4,
            y- 0.5f,
            x - 0.5f,
            y + height- 0.5f,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 30),
            true
        )

        /** draw text */
        Main.fontManager.drawStringWithShadow(matrices,
            text,
            (x + (width / 2.0f)) - (Main.fontManager.getStringWidth(text) / 2.0f),
            (y + (height / 2.0f)) - (Main.fontManager.getHeight() / 2.0f),
            Color.WHITE
        )
    }

    fun setup(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }
}
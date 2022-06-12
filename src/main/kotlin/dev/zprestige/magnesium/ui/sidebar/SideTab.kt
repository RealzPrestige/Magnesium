package dev.zprestige.magnesium.ui.sidebar

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.ui.ClickGui
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import java.awt.Color
import java.util.*

class SideTab(val name: String) {
    private val icon: Identifier = Identifier("magnesium", "icons/${name.lowercase().split(" ")[0]}.png")
    var x: Float = 0.0f
    var y: Float = 0.0f
    var width: Float = 0.0f
    var height: Float = 15.0f
    var color = Color(200, 200, 200, 255)

    fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {
        /** checks if inside bounding box */
        val hovering = mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height

        /** if we click we set the selected sidebar to this */
        if (clickFrame && hovering) {
            ClickGui.sidebar = this
        }
        /** if hovering or selected our color should be white */
        val white = hovering || ClickGui.sidebar == this

        /** change color accordingly */
        color = if (white) {
            Color(convertGrayToWhite(color.red), convertGrayToWhite(color.green), convertGrayToWhite(color.blue))
        } else {
            Color(convertWhiteToGray(color.red), convertWhiteToGray(color.green), convertWhiteToGray(color.blue))
        }
        /** box behind tab */
        RenderUtil.fill(matrices, x + 2, y, x + width - 2, y + height, Color(0, 0, 0, 30))

        /** gradients on both sides */
        RenderUtil.fillGradient(matrices, x + width - 2.5f, y, x + width, y + height, Color(0, 0, 0, 30), Color(0, 0, 0, 0), true)
        RenderUtil.fillGradient(matrices, x, y, x + 2, y + height, Color(0, 0, 0, 0), Color(0, 0, 0, 30), true)

        /** draw the name of side tab */
        Main.fontManager.prepare(0.95f).drawStringWithShadow(matrices,
            name,
            x + 18.0f,
            y + (height / 2.0f) - (Main.fontManager.getHeight(0.95f) / 2.0f),
            color
        ).pop()

        /** draw icon */
        RenderUtil.drawTexture(matrices, icon, x.toInt() + 1, y.toInt() + 1, 0, 0.0f, 0.0f, 13, 13, 13, 13)
    }

    fun setup(x: Float, y: Float, width: Float) {
        this.x = x
        this.y = y
        this.width = width
    }

    private fun convertWhiteToGray(input: Int): Int {
        return if (input > 150) {
            input - 2
        } else input
    }

    private fun convertGrayToWhite(input: Int): Int {
        return if (input < 254) {
            input + 2
        } else input
    }
}
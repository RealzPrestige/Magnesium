package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main.Companion.mc
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

class FontManager {

    fun drawString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color) {
        mc.textRenderer.draw(matrixStack, text, x, y, color.rgb)
    }

    fun drawString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color, scale: Float) {
        mc.textRenderer.draw(matrixStack, text, x / scale, y / scale, color.rgb)
    }

    fun drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color) {
        drawString(matrixStack, text, x + 1.0f, y + 1.0f, Color(0, 0, 0, 100))
        drawString(matrixStack, text, x, y, color)
    }

    fun drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color, scale: Float) {
        drawString(matrixStack, text, (x + 1.0f) / scale, (y + 1.0f )/ scale, Color(0, 0, 0, 100))
        drawString(matrixStack, text, x / scale, y / scale, color)
    }

    fun getStringWidth(text: String): Int {
        return mc.textRenderer.getWidth(text)
    }

    fun getStringWidth(text: String, scale: Float): Float {
        return mc.textRenderer.getWidth(text) * scale
    }

    fun getHeight(): Int {
        return mc.textRenderer.fontHeight
    }

    fun getHeight(scale: Float): Float {
        return mc.textRenderer.fontHeight * scale
    }

    inner class prepare(private val scale: Float) {
        private var matrixStack: MatrixStack? = null
        private var pushed: Boolean = false

        inner class drawString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color) {
            init {
                this@prepare.push(matrixStack)
                this@FontManager.drawString(matrixStack, text, x, y, color, scale)
            }

            fun drawString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color): drawString {
                return this@prepare.drawString(matrixStack, text, x, y, color)
            }

            fun drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color): drawStringWithShadow {
                return this@prepare.drawStringWithShadow(matrixStack, text, x, y, color)
            }

            fun pop() {
                this@prepare.pop()
            }
        }

        inner class drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color) {
            init {
                this@prepare.push(matrixStack)
                this@FontManager.drawStringWithShadow(matrixStack, text, x, y, color, scale)
            }

            fun drawString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color): drawString {
                return this@prepare.drawString(matrixStack, text, x, y, color)
            }

            fun drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Color): drawStringWithShadow {
                return this@prepare.drawStringWithShadow(matrixStack, text, x, y, color)
            }

            fun pop() {
                this@prepare.pop()
            }
        }
        private fun push(matrixStack: MatrixStack) {
            if (!pushed) {
                this.matrixStack = matrixStack
                matrixStack.push()
                matrixStack.scale(scale, scale, scale)
                pushed = true
            }
        }

        fun pop() {
            matrixStack?.pop()
        }
    }
}
package dev.zprestige.magnesium.ui.selected

import dev.zprestige.magnesium.ui.button.Button
import net.minecraft.client.util.math.MatrixStack

open class Selected(val name: String) {
    var addY = 5.0f
    var tab: String = ""
    var x: Float = 0.0f
    var y: Float = 0.0f
    var width: Float = 0.0f
    var height: Float = 0.0f
    var buttons: Array<Button> = arrayOf()

    open fun renderInner(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
    }

    open fun renderButtons(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {
        for (b in buttons) {
            b.selected = tab == b.text
        }
        var deltaY = 0.0f
        buttons.forEach { button ->
            button.setup(width / 3.5f + 20.0f + deltaY, y + 7.5f, 100.0f, 15.0f)
            deltaY += 110.0f
        }
        buttons.forEach { button -> button.render(matrices, mouseX, mouseY, clickFrame) }
    }

    open fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double) {
    }

    open fun setup(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    open fun keyPressed(keyCode: Int) {}

    open fun charTyped(chr: Char) {}

    open fun createButton(name: String, runnable: Runnable, selected: Boolean): Button{
        return Button(name, runnable, selected)
    }
}
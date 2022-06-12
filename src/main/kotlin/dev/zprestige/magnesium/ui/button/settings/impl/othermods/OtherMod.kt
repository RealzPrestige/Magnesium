package dev.zprestige.magnesium.ui.button.settings.impl.othermods

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.ui.selected.impl.OtherMods
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Util
import java.awt.Color
import java.io.File


class OtherMod(val mod: OtherMods.Mod, var x: Float, var y: Float, var width: Float, var height: Float) {
    private val dir = File("${Main.mc.runDirectory}${File.separator}mods")
    private var mods: Array<File> = dir.listFiles()!!
    var color: Color = Color(150, 150, 150)


    fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {
        mods = dir.listFiles()!!
        RenderUtil.fill(matrices, x, y, x + width, y + height, Color(0, 0, 0, 30))
        RenderUtil.fillGradient(matrices,
            x + width - 0.25f,
            y,
            x + width + 4,
            y + height,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 0),
            true
        )
        RenderUtil.fillGradient(matrices,
            x - 4,
            y,
            x - 0.25f,
            y + height,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 30),
            true
        )
        Main.fontManager.drawStringWithShadow(matrices,
            mod.modName,
            x + (width / 2.0f) - (Main.fontManager.getStringWidth(mod.modName) / 2.0f),
            y + 5.0f,
            Color.WHITE
        )
        var installed = false
        for (m in mods) {
            if (m.name.lowercase().contains(mod.modName.lowercase())) {
                installed = true
            }
        }
        val color = if (installed) {
            Color(0, 255, 0, 30)
        } else {
            Color(0, 0, 0, 30)
        }
        RenderUtil.fillGradient(matrices,
            x + width / 2.0f - 50.0f,
            y + height - 20.0f,
            x + width / 2.0f + 50.0f,
            y + height - 5.0f,
            color,
            color,
            true
        )
        val inside =
            mouseX > x + width / 2.0f - 50.0f && mouseX < x + width / 2.0f + 50.0f && mouseY > y + height - 20.0f && mouseY < y + height - 5.0f
        this.color = if (inside) {
            Color(convertGrayToWhite(this.color.red),
                convertGrayToWhite(this.color.green),
                convertGrayToWhite(this.color.blue)
            )
        } else {
            Color(convertWhiteToGray(this.color.red),
                convertWhiteToGray(this.color.green),
                convertWhiteToGray(this.color.blue)
            )
        }
        val text = if (installed) {
            "Installed"
        } else {
            "Install"
        }
        Main.fontManager.drawStringWithShadow(matrices,
            text,
            x + width / 2.0f - (Main.fontManager.getStringWidth(text) / 2.0f),
            y + height - 12.5f - (Main.fontManager.getHeight() / 2.0f),
            this.color
        )
        if (!installed && clickFrame && inside) {
            Util.getOperatingSystem().open(mod.url)
        }
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
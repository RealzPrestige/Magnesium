package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color


class Latency : Feature("Latency", "Renders latency (ms)") {
    private val renderText = inscribe("Render Text",
        "[{VALUE}{TEXT}]",
        arrayOf("[{VALUE}{TEXT}]", "[{TEXT}: {VALUE}]", "{VALUE}{TEXT}", "{TEXT}: {VALUE}")
    ).tab("Text")
    private val text = inscribe("Text", "Ping", arrayOf("Ping", "ms", "Latency")).tab("Text")
    private val capitalized = inscribe("Capitalized", true).tab("Text")
    private val shadow = inscribe("Shadow", true).tab("Rendering")
    private val scale = inscribe("Scale", 1.0f, 0.1f, 5.0f).tab("Rendering")
    private val color = inscribe("Color", Color.WHITE).tab("Rendering")

    init {
        hudComponent = HudComponent(0.0f, 0.0f, 0.0f, 0.0f)
    }

    override fun renderHud(matrixStack: MatrixStack) {
        val latency = Main.mc.networkHandler?.getPlayerListEntry(Main.mc.player?.uuid)?.latency.toString()
        val text = this.text.value
        var drawText = when (renderText.value) {
            "[{VALUE}{TEXT}]" -> "[$latency$text]"
            "[{TEXT}: {VALUE}]" -> "[$text: $latency]"
            "{VALUE}{TEXT}" -> "$latency$text"
            "{TEXT}: {VALUE}" -> "$text: $latency"
            else -> {
                latency
            }
        }
        if (capitalized.value) {
            drawText = drawText.uppercase()
        }
        hudComponent!!.width = Main.fontManager.getStringWidth(drawText, scale.value)
        hudComponent!!.height = Main.fontManager.getHeight(scale.value)
        if (shadow.value) {
            Main.fontManager.prepare(scale.value)
                .drawStringWithShadow(matrixStack, drawText, hudComponent!!.x, hudComponent!!.y, color.value).pop()
        } else {
            Main.fontManager.prepare(scale.value)
                .drawString(matrixStack, drawText, hudComponent!!.x, hudComponent!!.y, color.value).pop()
        }
    }
}
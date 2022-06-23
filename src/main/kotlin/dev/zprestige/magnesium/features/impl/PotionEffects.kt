package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.StatusEffectOverlayEvent
import dev.zprestige.magnesium.features.Feature
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color
import java.util.concurrent.TimeUnit


class PotionEffects : Feature("PotionEffects", "Displays which potions are active together with its amplifier and duration") {
    private val capitalized = inscribe("Capitalized", true).tab("Text")
    private val removeDefaultIcons = inscribe("Remove Default Icons", true).tab("Rendering")
    private val shadow = inscribe("Shadow", true).tab("Rendering")
    private val scale = inscribe("Scale", 1.0f, 0.1f, 5.0f).tab("Rendering")
    private val color = inscribe("Color", Color.WHITE).tab("Rendering")

    init {
        hudComponent = HudComponent(0.0f, 0.0f, 0.0f, 0.0f)
    }

    override fun renderHud(matrixStack: MatrixStack) {
        if (nullCheck()) {
            val width = mc.window.scaledWidth / 2.0f
            val height = mc.window.scaledHeight / 2.0f
            var deltaY = 0.0f
            val x = hudComponent!!.x
            val y = hudComponent!!.y
            val top = y > height
            val right = x > width
            val texts = ArrayList<TextWidth>()
            for (effect in mc.player!!.statusEffects) {
                val amplifier = effect.amplifier
                val duration: Int = effect.duration / 20
                val minutes = TimeUnit.SECONDS.toMinutes(duration.toLong())
                var seconds = (duration - TimeUnit.MINUTES.toSeconds(minutes)).toString()
                if (seconds.length == 1){
                    seconds = "0$seconds"
                }
                val formattedDuration: String = if (effect.isPermanent) {
                    if (right) {
                        "   ∞"
                    } else {
                        "∞"
                    }
                } else {
                    "$minutes:$seconds"
                }

                val name = effect.toString().split(".")[2].split(" ")[0].replace(",", " ")
                var name1 = ""
                var prevChar: Char? = null
                for (char in name.toCharArray()) {
                    name1 += if (prevChar == "_".toCharArray()[0] || prevChar == null) {
                        char.uppercaseChar()
                    } else {
                        char
                    }
                    prevChar = char
                }
                name1 = name1.replace(" ", "").replace("_", " ")
                var text = "$name1 $amplifier $formattedDuration".replace("0 ", "1 ")
                if (capitalized.value) {
                    text = text.uppercase()
                }
                texts.add(TextWidth(text))
            }
                texts.sortWith(Comparator.comparing(TextWidth::getWidth))
            for (effect in texts) {
                val text = effect.string
                val x1 = if (right) {
                    x - Main.fontManager.getStringWidth(text, scale.value)
                } else {
                    x
                }
                if (shadow.value) {
                    Main.fontManager.prepare(scale.value).drawStringWithShadow(matrixStack,
                        text,
                        x1,
                        y + deltaY,
                        color.value
                    ).pop()
                } else {
                    Main.fontManager.prepare(scale.value).drawString(matrixStack,
                        text,
                        x1,
                        y + deltaY,
                        color.value
                    ).pop()
                }
                val textHeight = Main.fontManager.getHeight(scale.value)
                deltaY += if (top) {
                    -textHeight
                } else {
                    textHeight
                }
            }
            hudComponent!!.width = 5.0f
            hudComponent!!.height = 5.0f
        }
    }

    @Listener
    fun onStatusEffectOverlay() = registerListener<StatusEffectOverlayEvent> {
        if (removeDefaultIcons.value){
            it.cancelled = true
        }
    }

    inner class TextWidth(val string: String) {

        fun getWidth(): Float {
            return -Main.fontManager.getStringWidth(string, scale.value)
        }
    }
}
package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color
import kotlin.math.round

class Coordinates : Feature("Coordinates", "Displays your coordinates") {
    private val renderText = inscribe("Render Text", "X: {X}, Y: {Y}, Z: {Z} {BRACES}X: {OX}, Z: {OZ}{BRACES}", arrayOf(
        "X: {X}, Y: {Y}, Z: {Z} {BRACES}X: {OX}, Z: {OZ}{BRACES}",
        "XYZ {X}, {Y}, {Z} {BRACES}{OX}, {OZ}{BRACES}",
    )
    ).tab("Text")
    private val braces = inscribe("Braces", "[", arrayOf("[", "("))
    private val capitalized = inscribe("Capitalized", true).tab("Text")
    private val shadow = inscribe("Shadow", true).tab("Rendering")
    private val scale = inscribe("Scale", 1.0f, 0.1f, 5.0f).tab("Rendering")
    private val color = inscribe("Color", Color.WHITE).tab("Rendering")

    init {
        hudComponent = HudComponent(0.0f, 0.0f, 0.0f, 0.0f)
    }

    override fun renderHud(matrixStack: MatrixStack) {
        val x = round(mc.player!!.pos.x)
        val y = round(mc.player!!.pos.y)
        val z = round(mc.player!!.pos.z)
        println(mc.world!!.dimension.coordinateScale)
        var factor = mc.world!!.dimension.coordinateScale
        if (factor == 1.0){
            factor = 0.125
        }
        val secondX = round(x * factor)
        val secondZ = round(z * factor)
        var openBracket = ""
        var closingBracket = ""
        when (braces.value) {
            "[" -> {
                openBracket = "["
                closingBracket = "]"
            }
            "(" -> {
                openBracket = "("
                closingBracket = ")"
            }
        }
        var text = ""
        when (renderText.value) {
            "X: {X}, Y: {Y}, Z: {Z} {BRACES}X: {OX}, Z: {OZ}{BRACES}" -> {
                text = "x: $x, y: $y, z: $z ${openBracket}x: $secondX, z: $secondZ$closingBracket"
            }
            "XYZ {X}, {Y}, {Z} {BRACES}{OX}, {OZ}{BRACES}" -> {
                text = "xyz: $x, $y, $z $openBracket$secondX, $secondZ$closingBracket"
            }
        }
        if (capitalized.value) {
            text = text.uppercase()
        }
        if (shadow.value) {
            Main.fontManager.prepare(scale.value)
                .drawStringWithShadow(matrixStack, text, hudComponent!!.x, hudComponent!!.y, color.value).pop()
        } else {
            Main.fontManager.prepare(scale.value)
                .drawString(matrixStack, text, hudComponent!!.x, hudComponent!!.y, color.value).pop()
        }
        hudComponent!!.width = Main.fontManager.getStringWidth(text, scale.value)
        hudComponent!!.height = Main.fontManager.getHeight(scale.value)
    }
}
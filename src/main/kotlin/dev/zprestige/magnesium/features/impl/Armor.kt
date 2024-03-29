package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.impl.Render2DEvent
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.item.ItemStack
import java.awt.Color
import kotlin.math.ceil

class Armor : Feature("Armor", "Displays armor with durability") {
    private val reversed = inscribe("Reversed", false)
    private val fixUnderWater = inscribe("Fix Under Water", false)
    private val removeDecimal = inscribe("Remove Decimal", false)
    private val removePercentage = inscribe("Remove %", false)
    private val renderMode = inscribe("Render Mode", "Horizontal", arrayOf("Horizontal", "Vertical"))

    init {
        hudComponent = HudComponent(0.0f, 0.0f, 0.0f, 0.0f)
    }

    @Listener
    fun onRender2D() = registerListener<Render2DEvent> {
        val matrices = it.matrixStack
        val itemRenderer = mc.itemRenderer
        var deltaX = 0.0f
        var deltaY = 0.0f
        var armorItems = mc.player!!.armorItems
        if (reversed.value) {
            armorItems = armorItems.reversed()
        }
        val addY = if (fixUnderWater.value && mc.player!!.isSubmergedInWater) {
            10.0f
        } else {
            0.0f
        }
        val horizontal = renderMode.value == "Horizontal"
        val addX = if (horizontal) {
            10.0f
        } else {
            8.0f
        }
        for (armor in armorItems) {
            if (!armor.isEmpty) {
                val percentage = ceil(getPercentage(armor))
                val color = Color(redByPercentage(percentage), greenByPercentage(percentage), 0.0f)
                var percentage1 = percentage.toString()
                if (removeDecimal.value) {
                    percentage1 = percentage.toString().replace(".0", "")
                }
                val icon = if (removePercentage.value) {
                    ""
                } else {
                    "%"
                }
                val renderString = "$percentage1$icon"
                Main.fontManager.prepare(0.7f).drawStringWithShadow(
                    matrices,
                    renderString,
                    hudComponent!!.x + deltaX + addX - (Main.fontManager.getStringWidth(renderString, 0.7f) / 2.0f),
                    hudComponent!!.y + deltaY - Main.fontManager.getHeight(0.7f) - addY,
                    color
                ).pop()
            }
            itemRenderer.renderGuiItemIcon(armor,
                (hudComponent!!.x + deltaX).toInt(),
                (hudComponent!!.y + deltaY - addY).toInt()
            )
            when (renderMode.value) {
                "Horizontal" -> deltaX += 20.0f
                "Vertical" -> deltaY += 20.0f
            }
        }
        hudComponent!!.width = if (horizontal) {
            deltaX
        } else {
            16.0f
        }
        hudComponent!!.height = if (horizontal) {
            16.0f
        } else {
            deltaY
        }
    }

    private fun getPercentage(stack: ItemStack): Float {
        val durability: Float = (stack.maxDamage - stack.damage).toFloat()
        return durability / stack.maxDamage * 100.0f
    }

    private fun redByPercentage(percentage: Float): Float {
        return (255.0f - percentage * 2.5f) / 255.0f
    }

    private fun greenByPercentage(percentage: Float): Float {
        return (percentage * 2.5f) / 255.0f
    }
}
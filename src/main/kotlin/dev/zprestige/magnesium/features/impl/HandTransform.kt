package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.RenderHandEvent
import dev.zprestige.magnesium.event.impl.TickEvent
import dev.zprestige.magnesium.features.Feature
import net.minecraft.text.Text

class HandTransform : Feature("Hand Transform", "Transforms the look of your items") {
    private val x = inscribe("x", 0.0f, -2.0f, 2.0f)
    private val y = inscribe("y", 0.0f, -2.0f, 2.0f)
    private val z = inscribe("z", 0.0f, -2.0f, 2.0f)
    private val scaleX = inscribe("Scale x", 1.0f, 0.1f, 2.0f)
    private val scaleY = inscribe("Scale y", 1.0f, 0.1f, 2.0f)
    private val scaleZ = inscribe("Scale z", 1.0f, 0.1f, 2.0f)
    private var purity = false
    private var announced = false

    @Listener
    fun onTick() = registerListener<TickEvent> {
        if (Main.serverManager.purity && !announced && !purity) {
            announced = true
            purity = true
        } else if (!Main.serverManager.purity) {
            purity = false
        }
        if (announced && mc.player != null) {
            Main.mc.player!!.sendMessage(Text.of("You are connected to purityvanilla, HandTransform will be forcefully disabled"),
                true
            )
            announced = false
        }
    }

    override fun onEnable() {
        if (purity) {
            Main.mc.player!!.sendMessage(Text.of("You are connected to purityvanilla, HandTransform will be forcefully disabled"),
                true
            )
        }
    }


    @Listener
    fun onRenderHand() = registerListener<RenderHandEvent> {
        if (!Main.serverManager.purity) {
            it.cancelled = true
            it.x = x.value
            it.y = y.value
            it.z = z.value
            it.scaleX = scaleX.value
            it.scaleY = scaleY.value
            it.scaleZ = scaleZ.value
        }
    }
}
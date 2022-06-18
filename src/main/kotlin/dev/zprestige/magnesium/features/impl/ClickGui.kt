package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.TickEvent
import dev.zprestige.magnesium.features.Feature
import net.minecraft.client.particle.TotemParticle
import net.minecraft.item.Items

class ClickGui : Feature("ClickGui", "Displays the Magnesium client's clickgui") {
    companion object {
        lateinit var Instance: ClickGui
    }
    init {
        Instance = this
    }
    init {
        keybind.value = 79
    }

    override fun onEnable() {
        if (nullCheck()) {
            mc.setScreen(dev.zprestige.magnesium.ui.ClickGui())
        }
    }

    override fun onDisable() {
        mc.setScreen(null)
        Main.configManager.save()
    }

    @EventListener
    fun onTick() = eventListener<TickEvent> {
        if (mc.currentScreen == null){
            disable()
        }
    }
}
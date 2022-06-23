package dev.zprestige.magnesium.features

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.Setting
import dev.zprestige.magnesium.settings.impl.*
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

open class Feature(var name: String, var description: String) {
    val mc: MinecraftClient = Main.mc
    var settings: ArrayList<Setting<*>> = ArrayList()
    var keybind: Keybind = inscribe("Keybind", -1)
    var enabled: Switch  = inscribe("Enabled", false)
    var hudComponent: HudComponent? = null

    open fun onEnable() {}

    open fun onDisable() {}

    open fun renderHud(matrixStack: MatrixStack) {
    }

    fun enable() {
        Main.eventBus.register(this)
        onEnable()
        enabled.value = true
    }

    fun disable() {
        Main.eventBus.unregister(this)
        onDisable()
        enabled.value = false
    }

    fun nullCheck(): Boolean {
        return mc.world != null && mc.player != null
    }

    fun toggle() = if (enabled.value) disable() else enable()

    fun inscribe(name: String, value: Color): ColorBox {
        val setting = ColorBox(name, value)
        settings.add(setting)
        return setting
    }
    fun inscribe(name: String, value: String, modes: Array<String>): Combo {
        val setting = Combo(name, value, modes)
        settings.add(setting)
        return setting
    }
    fun inscribe(name: String, value: Int): Keybind {
        val setting = Keybind(name, value)
        settings.add(setting)
        return setting
    }
    fun inscribe(name: String, value: Float, min: Float, max: Float): SliderFloat {
        val setting = SliderFloat(name, value, min, max)
        settings.add(setting)
        return setting
    }
    fun inscribe(name: String, value: Int, min: Int, max: Int): SliderInt {
        val setting = SliderInt(name, value, min, max)
        settings.add(setting)
        return setting
    }
    fun inscribe(name: String, value: Boolean): Switch {
        val setting = Switch(name, value)
        settings.add(setting)
        return setting
    }

    class HudComponent(var x: Float, var y: Float, var width: Float, var height: Float)
}
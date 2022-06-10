package dev.zprestige.magnesium.features

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.settings.impl.*
import net.minecraft.client.MinecraftClient
import java.awt.Color

open class Feature(var name: String, var description: String) {
    val mc: MinecraftClient = Main.mc
    var keybind: Keybind = inscribe("Keybind", -1)
    var enabled: Switch  = inscribe("Enabled", false)

    open fun onEnable() {}

    open fun onDisable() {}

    fun enable() {
        Main.eventBus.subscribe(this)
        onEnable()
        enabled.value = true
    }

    fun disable() {
        Main.eventBus.unsubscribe(this)
        onDisable()
        enabled.value = false
    }

    fun toggle() = if (enabled.value) disable() else enable()

    fun inscribe(name: String, value: Color): ColorBox = ColorBox(name, value)
    fun inscribe(name: String, value: String, modes: Array<String>): Combo = Combo(name, value, modes)
    fun inscribe(name: String, value: Int): Keybind = Keybind(name, value)
    fun inscribe(name: String, value: Float, min: Float, max: Float): SliderFloat = SliderFloat(name, value, min, max)
    fun inscribe(name: String, value: Int, min: Int, max: Int): SliderInt = SliderInt(name, value, min, max)
    fun inscribe(name: String, value: Boolean): Switch = Switch(name, value)
    fun inscribe(name: String, value: String): Text = Text(name, value)
}
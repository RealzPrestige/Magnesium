package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.features.Feature

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
    }
}
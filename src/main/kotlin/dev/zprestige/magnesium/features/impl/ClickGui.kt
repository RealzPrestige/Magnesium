package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.features.Feature

class ClickGui : Feature("ClickGui", "Displays the Magnesium client's clickgui") {

    init {
        keybind.value = 79
    }

    override fun onEnable() {
        mc.setScreen(dev.zprestige.magnesium.ui.ClickGui())
    }
}
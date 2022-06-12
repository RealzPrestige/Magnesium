package dev.zprestige.magnesium.settings.impl

import dev.zprestige.magnesium.settings.Setting

class SliderFloat(name: String, value: Float, var min: Float, var max: Float) : Setting<Float>(name, value) {

    fun tab(tab: String): SliderFloat {
        this.tab = tab
        return this
    }
}
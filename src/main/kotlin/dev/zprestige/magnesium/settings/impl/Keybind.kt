package dev.zprestige.magnesium.settings.impl

import dev.zprestige.magnesium.settings.Setting

class Keybind(name: String, value: Int) : Setting<Int>(name, value) {

    fun tab(tab: String): Keybind {
        this.tab = tab
        return this
    }
}
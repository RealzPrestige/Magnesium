package dev.zprestige.magnesium.settings.impl

import dev.zprestige.magnesium.settings.Setting

class Switch(name: String, value: Boolean) : Setting<Boolean>(name, value) {

    fun tab(tab: String): Switch {
        this.tab = tab
        return this
    }
}
package dev.zprestige.magnesium.settings.impl

import dev.zprestige.magnesium.settings.Setting

class Combo(name: String, value: String, var modes: Array<String>) : Setting<String>(name, value) {

    fun tab(tab: String):  Combo{
        this.tab = tab
        return this
    }
}


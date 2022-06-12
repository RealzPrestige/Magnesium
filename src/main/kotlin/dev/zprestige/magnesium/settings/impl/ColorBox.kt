package dev.zprestige.magnesium.settings.impl

import dev.zprestige.magnesium.settings.Setting
import java.awt.Color

class ColorBox(name: String, value: Color) : Setting<Color>(name, value) {

    fun tab(tab: String): ColorBox {
        this.tab = tab
        return this
    }
}
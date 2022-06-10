package dev.zprestige.magnesium.settings.impl

import dev.zprestige.magnesium.settings.Setting

class Keybind(name: String, value: Int) : Setting<Int>(name, value) {
    var hold: Boolean = false
}
package dev.zprestige.magnesium.settings.impl

import dev.zprestige.magnesium.settings.Setting

class SliderInt(name: String, value: Int, var min: Int, var max: Int) : Setting<Int>(name, value)
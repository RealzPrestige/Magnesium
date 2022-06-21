package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

class ColorFogEvent(var red: Float, var green: Float, var blue: Float) : Event(true)
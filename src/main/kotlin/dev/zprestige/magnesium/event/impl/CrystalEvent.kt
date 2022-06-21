package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

open class CrystalEvent : Event(true) {

    class Scale(var scale: Float) : CrystalEvent()

    class Rotation(var rotationSpeed: Float): CrystalEvent()

    class Bounce(var bounceSpeed: Float) : CrystalEvent()

    class Texture(var red: Float, var green: Float, var blue: Float, var alpha: Float) : CrystalEvent()
}
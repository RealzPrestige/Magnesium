package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

open class CrystalEvent : Event() {

    class Scale(var scale: Float) : Event()

    class Rotation(var rotationSpeed: Float) : Event()

    class Bounce(var bounceSpeed: Float) : Event()

    class Texture(var red: Float, var green: Float, var blue: Float, var alpha: Float) : Event()
}
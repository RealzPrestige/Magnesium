package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

class RenderHandEvent(var x: Float, var y: Float, var z: Float, var scaleX: Float, var scaleY: Float, var scaleZ: Float)  : Event(true)
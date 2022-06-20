package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

class RenderTerrainFogEvent(var start: Float, var end: Float) : Event(true) {
}
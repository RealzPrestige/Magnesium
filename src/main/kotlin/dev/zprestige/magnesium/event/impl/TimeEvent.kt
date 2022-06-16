package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

class TimeEvent(var time: Long) : Event(true)
package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

class KeyEvent(val key: Int, val action: Int) : Event(false) {

    class Release(val key: Int)
    class Press(val key: Int)
}
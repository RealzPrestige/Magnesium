package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event

class KeyEvent(val key: Int, val action: Int) : Event() {

    class Release(val key: Int) : Event()
    class Press(val key: Int) : Event()
}
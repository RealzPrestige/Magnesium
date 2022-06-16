package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.KeyEvent

class KeyManager {
    private val heldKeys = ArrayList<Int>()

    init {
        Main.eventBus.subscribe(this)
    }

    @EventListener
    fun onPress() = eventListener<KeyEvent.Press> { it ->
        val key = it.key
        Main.featureManager.features.filter {
            it.keybind.hold && it.keybind.value == key
        }.forEach {
            it.enable()
        }
    }

    @EventListener
    fun onRelease() = eventListener<KeyEvent.Release> { it ->
        val key = it.key
        Main.featureManager.features.filter {
            it.keybind.hold && it.keybind.value == key
        }.forEach {
            it.disable()
        }
    }

    @EventListener
    fun onKey() = eventListener<KeyEvent> {
        if (heldKeys.contains(it.key) && it.action == 0) {
            Main.eventBus.post(KeyEvent.Release(it.key))
            heldKeys.remove(it.key)
        }
        if (!heldKeys.contains(it.key) && (it.action == 2 || it.action == 1)) {
            Main.eventBus.post(KeyEvent.Press(it.key))
            heldKeys.add(it.key)
        }
    }

    fun isKeyHeld(key: Int): Boolean {
        return heldKeys.contains(key)
    }
}
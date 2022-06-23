package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener

import dev.zprestige.magnesium.event.impl.KeyEvent

class KeyManager {
    private val heldKeys = ArrayList<Int>()

    init {
        Main.eventBus.register(this)
    }

    @Listener
    fun onPress() = registerListener<KeyEvent.Press> { it ->
        val key = it.key
        Main.featureManager.features.filter {
            it.keybind.hold && it.keybind.value == key
        }.forEach {
            it.enable()
        }
    }

    @Listener
    fun onRelease() = registerListener<KeyEvent.Release> { it ->
        val key = it.key
        Main.featureManager.features.filter {
            it.keybind.hold && it.keybind.value == key
        }.forEach {
            it.disable()
        }
    }

    @Listener
    fun onKey() = registerListener<KeyEvent> {
        if (heldKeys.contains(it.key) && it.action == 0) {
            Main.eventBus.invoke(KeyEvent.Release(it.key))
            heldKeys.remove(it.key)
        }
        if (!heldKeys.contains(it.key) && (it.action == 2 || it.action == 1)) {
            Main.eventBus.invoke(KeyEvent.Press(it.key))
            heldKeys.add(it.key)
        }
    }

    fun isKeyHeld(key: Int): Boolean {
        return heldKeys.contains(key)
    }
}
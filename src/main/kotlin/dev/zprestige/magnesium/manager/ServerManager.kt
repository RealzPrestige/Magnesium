package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.ConnectEvent

class ServerManager {
    var purity = false

    init {
        Main.eventBus.register(this)
    }

    @Listener
    fun onConnect() = registerListener<ConnectEvent> {
        purity = it.address.lowercase().contains("purity")
    }
}
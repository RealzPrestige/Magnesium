package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener

import dev.zprestige.magnesium.event.impl.Render2DEvent

class HudManager {

    init {
        Main.eventBus.register(this)
    }

    @Listener
    fun onRender2D() = registerListener<Render2DEvent> {
        for (f in Main.featureManager.features){
            if (f.hudComponent != null && f.enabled.value){
                f.renderHud(it.matrixStack)
            }
        }
    }
}
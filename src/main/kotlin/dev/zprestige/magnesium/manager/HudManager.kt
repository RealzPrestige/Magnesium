package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.Render2DEvent

class HudManager {

    init {
        Main.eventBus.subscribe(this)
    }

    @EventListener
    fun onRender2D() = eventListener<Render2DEvent> {
        for (f in Main.featureManager.features){
            if (f.hudComponent != null && f.enabled.value){
                f.renderHud(it.matrixStack)
            }
        }
    }
}
package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.Render3DEvent
import dev.zprestige.magnesium.features.Feature

class Blur : Feature("Blur", "Blurs the background on screens") {
    private val strength = inscribe("Strength", 5.0f, 0.1f, 50.0f)
    private val fadeSpeed = inscribe("Fade Speed", 20.0f, 0.1f, 100.0f)

    @Listener
    fun render3D() = registerListener<Render3DEvent> {
        Main.blurManager.updateBlur(strength.value, fadeSpeed.value)
    }

    override fun onDisable() {
        Main.blurManager.blur(0.0f)
    }
}
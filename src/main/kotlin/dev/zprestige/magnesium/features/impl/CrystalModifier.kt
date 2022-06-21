package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.CrystalEvent
import dev.zprestige.magnesium.features.Feature
import java.awt.Color

class CrystalModifier : Feature("Crystal Modifier", "Modifies the way end crystals look") {
    private val scale = inscribe("Scale", 1.0f, 0.1f, 1.5f)
    private val rotationSpeed = inscribe("Rotation Speed", 1.0f, 0.0f, 5.0f)
    private val bounceSpeed = inscribe("Bounce Speed", 1.0f, 0.0f, 2.0f)
    private val emptyTexture = inscribe("Empty Texture", false)
    private val colorCrystal = inscribe("Color Crystal", Color.WHITE)

    @EventListener
    fun onCrystalScale() = eventListener<CrystalEvent.Scale> {
        it.cancel()
        it.scale = scale.value
    }

    @EventListener
    fun onCrystalRotation() = eventListener<CrystalEvent.Rotation> {
        it.cancel()
        it.rotationSpeed = rotationSpeed.value
    }

    @EventListener
    fun onCrystalBounce() = eventListener<CrystalEvent.Bounce> {
        it.cancel()
        it.bounceSpeed = bounceSpeed.value
    }
}
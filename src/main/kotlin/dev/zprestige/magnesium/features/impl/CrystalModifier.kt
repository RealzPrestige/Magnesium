package dev.zprestige.magnesium.features.impl


import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.CrystalEvent
import dev.zprestige.magnesium.features.Feature
import java.awt.Color

class CrystalModifier : Feature("Crystal Modifier", "Modifies the way end crystals look") {
    private val scale = inscribe("Scale", 1.0f, 0.1f, 1.5f)
    private val rotationSpeed = inscribe("Rotation Speed", 1.0f, 0.0f, 5.0f)
    private val bounceSpeed = inscribe("Bounce Speed", 1.0f, 0.0f, 2.0f)
    private val emptyTexture = inscribe("Empty Texture", false)
    private val colorCrystal = inscribe("Color Crystal", Color.WHITE)

    @Listener
    fun onCrystalScale() = registerListener<CrystalEvent.Scale> {
        it.cancelled = true
        it.scale = scale.value
    }

    @Listener
    fun onCrystalRotation() = registerListener<CrystalEvent.Rotation> {
        it.cancelled = true
        it.rotationSpeed = rotationSpeed.value
    }

    @Listener
    fun onCrystalBounce() = registerListener<CrystalEvent.Bounce> {
        it.cancelled = true
        it.bounceSpeed = bounceSpeed.value
    }

    @Listener
    fun onCrystalTexture() = registerListener<CrystalEvent.Texture> {
        if (emptyTexture.value) {
            it.cancelled = true
            it.red = colorCrystal.value.red / 255.0f
            it.green = colorCrystal.value.green / 255.0f
            it.blue = colorCrystal.value.blue / 255.0f
            it.alpha = colorCrystal.value.alpha / 255.0f
        }
    }
}
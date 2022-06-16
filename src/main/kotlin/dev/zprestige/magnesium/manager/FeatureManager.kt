package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.features.impl.ClickGui
import dev.zprestige.magnesium.features.impl.Fps
import dev.zprestige.magnesium.features.impl.PotionEffects
import dev.zprestige.magnesium.features.impl.RemoveScoreboard

class FeatureManager {
    val features = ArrayList<Feature>()

    init {
        register(arrayOf(
            ClickGui(),
            Fps(),
            PotionEffects(),
            RemoveScoreboard()
        )
        )
    }

    private fun register(features: Array<Feature>) {
        this.features.addAll(features)
    }

    fun keyPressed(key: Int) {
        if (Main.mc.currentScreen == null) {
            features.forEach { feature ->
                if (feature.keybind.value == key) {
                    feature.toggle()
                }
            }
        }
    }
}
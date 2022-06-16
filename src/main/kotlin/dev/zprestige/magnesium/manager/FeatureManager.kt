package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.features.impl.*

class FeatureManager {
    val features = ArrayList<Feature>()

    init {
        register(arrayOf(
            ClickGui(),
            Fps(),
            Latency(),
            NoHurtCam(),
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
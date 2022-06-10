package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.features.impl.Bean
import dev.zprestige.magnesium.features.impl.ClickGui
import dev.zprestige.magnesium.features.impl.HIhi
import dev.zprestige.magnesium.features.impl.Joe

class FeatureManager {
    val features = ArrayList<Feature>()

    init {
        register(arrayOf(ClickGui(), Bean(), HIhi(), Joe()))
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
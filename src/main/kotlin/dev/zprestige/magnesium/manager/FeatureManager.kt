package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener

import dev.zprestige.magnesium.event.impl.KeyEvent
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.features.impl.*

class FeatureManager {
    val features = ArrayList<Feature>()

    init {
        register(arrayOf(
            Armor(),
            Chat(),
            ClickGui(),
            Coordinates(),
            Crosshair(),
            CrystalModifier(),
            Fps(),
            FullBright(),
            HandTransform(),
            Latency(),
            LowFire(),
            NoHurtCam(),
            PotionEffects(),
            RemoveDarkBackground(),
            RemoveScoreboard(),
            StaticItems(),
            TimeChanger(),
            TotemAnimation(),
            Zoom()
        )
        )
        Main.eventBus.register(this)
    }

    private fun register(features: Array<Feature>) {
        this.features.addAll(features)
    }

    @Listener
    fun keyPressed() = registerListener<KeyEvent> {
        if (Main.mc.currentScreen == null) {
            features.filter {
                !it.keybind.hold
            }.forEach { feature ->
                if (feature.keybind.value == it.key && it.action == 1) {
                    feature.toggle()
                }
            }
        }
    }
}
package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.KeyEvent
import dev.zprestige.magnesium.event.impl.ZoomEvent
import dev.zprestige.magnesium.features.Feature

class Zoom : Feature("Zoom", "Zooms in by fov") {
    private val zoomBind = inscribe("Bind", -1)
    private val zoomedFov = inscribe("Fov", 30.0f, 10.0f, 50.0f)
    private val animate = inscribe("Animate", false)
    private val animationSpeed = inscribe("Animation Speed", 1.0f, 0.1f, 10.0f)
    private val cinematic = inscribe("Cinematic", false)
    private var zoom = 0.0
    private var pressed = false
    private var smooth = false

    @EventListener
    fun onZoom() = eventListener<ZoomEvent> {
        if (mc.currentScreen == null) {
            if (zoomBind.value != -1) {
                if (zoomBind.hold) {
                    if (Main.keyManager.isKeyHeld(zoomBind.value)) {
                        if (animate.value) {
                            zoom += (1.0f - zoom) / (50.0f / animationSpeed.value)
                        } else {
                            it.fov = zoomedFov.value.toDouble()
                        }
                        if (cinematic.value) {
                            cinematic()
                        }
                    } else {
                        if (animate.value) {
                            zoom -= zoom / (50.0f / animationSpeed.value)
                        }
                        uncinematize()
                    }
                    it.fov -= (mc.options.fov - zoomedFov.value) * zoom
                } else if (pressed) {
                    if (cinematic.value) {
                        cinematic()
                    }
                    it.fov = zoomedFov.value.toDouble()
                } else {
                    uncinematize()
                }
            }
        }
    }

    @EventListener
    fun onPress() = eventListener<KeyEvent.Press> {
        if (it.key == zoomBind.value) {
            pressed = !pressed
        }
    }

    private fun cinematic() {
        mc.options.smoothCameraEnabled = true
        smooth = true
    }

    private fun uncinematize() {
        if (smooth) {
            mc.options.smoothCameraEnabled = false
            smooth = false
        }
    }
}
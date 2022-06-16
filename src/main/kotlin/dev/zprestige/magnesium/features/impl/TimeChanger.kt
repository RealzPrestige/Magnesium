package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.Render3DEvent
import dev.zprestige.magnesium.event.impl.TimeEvent
import dev.zprestige.magnesium.features.Feature
import kotlin.math.min
import kotlin.math.roundToLong

class TimeChanger : Feature("Time Changer", "Changes the time of the world") {
    private val time = inscribe("Time", 10000, 0, 24000)
    private val animate = inscribe("Animate", 0.0f, 0.0f, 1.0f)
    private var animation = 0.0f

    @EventListener
    fun onRender3D() = eventListener<Render3DEvent> {
        animation = min(1.0f, (animation + (0.001f * animate.value)))
        if (animation >= 1.0f){
            animation = 0.0f
        }
    }

    @EventListener
    fun onTime() = eventListener<TimeEvent> {
        if (animate.value > 0.0f){
            it.time = (24000 * animation).roundToLong()
        } else {
            it.time = time.value.toLong()
        }
    }
}
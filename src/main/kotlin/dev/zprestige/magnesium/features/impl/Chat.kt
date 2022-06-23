package dev.zprestige.magnesium.features.impl


import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.ClearChatEvent
import dev.zprestige.magnesium.event.impl.Render2DEvent
import dev.zprestige.magnesium.features.Feature

class Chat : Feature("Chat", "Modifies the looks of chat") {
    private val scale = inscribe("Scale", 1.0f, 0.1f, 2.0f)
    private val textOpacity = inscribe("Text Opacity", 255.0f, 0.1f, 255.0f)
    private val backgroundOpacity = inscribe("Background Opacity", 100.0f, 0.1f, 255.0f)
    private val infinite = inscribe("Infinite", false)

    @Listener
    fun onRender2D() = registerListener<Render2DEvent> {
        mc.options.chatOpacity = textOpacity.value.toDouble() / 255.0f
        mc.options.textBackgroundOpacity = backgroundOpacity.value.toDouble() / 255.0f
        mc.options.chatScale = scale.value.toDouble()
    }

    @Listener
    fun onClearChat() = registerListener<ClearChatEvent> {
        if (infinite.value) {
            it.cancelled = true
        }
    }

}
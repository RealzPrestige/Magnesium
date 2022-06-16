package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.AddMessageEvent
import dev.zprestige.magnesium.event.impl.ClearChatEvent
import dev.zprestige.magnesium.event.impl.Render2DEvent
import dev.zprestige.magnesium.features.Feature
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents

class Chat : Feature("Chat", "Modifies the looks of chat") {
    private val scale = inscribe("Scale", 1.0f, 0.1f, 2.0f)
    private val textOpacity = inscribe("Text Opacity", 255.0f, 0.1f, 255.0f)
    private val backgroundOpacity = inscribe("Background Opacity", 100.0f, 0.1f, 255.0f)
    private val infinite = inscribe("Infinite", false)
    private val nameMentionSound = inscribe("Name Mention Sound", false)

    @EventListener
    fun onRender2D() = eventListener<Render2DEvent> {
        mc.options.chatOpacity = textOpacity.value.toDouble() / 255.0f
        mc.options.textBackgroundOpacity = backgroundOpacity.value.toDouble() / 255.0f
        mc.options.chatScale = scale.value.toDouble()
    }

    @EventListener
    fun onClearChat() = eventListener<ClearChatEvent> {
        if (infinite.value){
            it.cancel()
        }
    }

    @EventListener
    fun highlightName() = eventListener<AddMessageEvent> {
        if (nameMentionSound.value) {
            val player = mc.player
            if (player != null) {
                val m = it.message.string.replace("<$name>", "").replace("$name>", "").replace("$name >", "")
                val name = player.displayName.asString()
                if (m.contains(name)) {
                    mc.soundManager.play(PositionedSoundInstance.master(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, 1.0f))
                }
            }
        }
    }
}
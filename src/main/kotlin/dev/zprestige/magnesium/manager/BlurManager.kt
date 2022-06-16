package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main.Companion.mc
import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.Render3DEvent
import ladysnake.satin.api.event.ShaderEffectRenderCallback
import ladysnake.satin.api.managed.ManagedShaderEffect
import ladysnake.satin.api.managed.ShaderEffectManager
import net.minecraft.util.Identifier

class BlurManager {
    private val blur: ManagedShaderEffect = ShaderEffectManager.getInstance()
        .manage(Identifier("blur", "shaders/post/fade_in_blur.json")) { shader: ManagedShaderEffect ->
            shader.setUniformValue("Radius", 10.0f)
        }
    private val blurProgress = blur.findUniform1f("Progress")
    var radius: Float = 0.0f

    init {
        ShaderEffectRenderCallback.EVENT.register(ShaderEffectRenderCallback { deltaTick: Float ->
            blurProgress.set(1.0f)
            blur.render(deltaTick)
        })
    }

    fun blur(radius: Float) {
        blur.setUniformValue("Radius", radius)
    }

    @EventListener
    fun render3D() = eventListener<Render3DEvent> {
        updateBlur()
    }

    private fun updateBlur() {
        if (mc.currentScreen != null){
            radius += ((5 - radius) / 20.0f)
        } else {
            radius = 0.0f
        }
        blur(radius)
    }
}
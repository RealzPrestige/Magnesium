package dev.zprestige.magnesium.mixins

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.impl.Render3DEvent
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(GameRenderer::class)
class MixinGameRenderer {

    @Inject(method = ["renderWorld"],
        at = [At(value = "INVOKE",
            target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"
        )]
    )
    private fun onRenderWorld(tickDelta: Float, limitTime: Long,  matrices: MatrixStack, callbackInfo: CallbackInfo) {
        val event = Render3DEvent(matrices)
        Main.eventBus.post(event)
    }
}
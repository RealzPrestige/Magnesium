package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.FireOverlayEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class MixinInGameOverlayRenderer {

    @Inject(method = "renderFireOverlay", at =@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V"), cancellable = true)
    private static void renderFireOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci){
        FireOverlayEvent event = new FireOverlayEvent(0.0f);
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()){
            ci.cancel();
            return;
        }
        matrices.translate(0.0f, event.getOffset(), 0.0f);
    }
}

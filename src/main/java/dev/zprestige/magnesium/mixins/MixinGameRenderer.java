package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.BobEvent;
import dev.zprestige.magnesium.event.impl.FloatingItemEvent;
import dev.zprestige.magnesium.event.impl.Render3DEvent;
import dev.zprestige.magnesium.event.impl.ZoomEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Shadow
    private int floatingItemTimeLeft;

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void getFov(CallbackInfoReturnable<Double> callbackInfoReturnable) {
        ZoomEvent event = new ZoomEvent(callbackInfoReturnable.getReturnValue());
        Main.Companion.getEventBus().post(event);
        callbackInfoReturnable.setReturnValue(event.getFov());
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void renderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        Main.Companion.getEventBus().post(new Render3DEvent(matrices));
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void bobViewWhenHurt(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo) {
        BobEvent event = new BobEvent();
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;floatingItemTimeLeft:I"))
    private void adjustFloatingTimeLeft(CallbackInfo ci) {
        FloatingItemEvent event = new FloatingItemEvent(0);
        Main.Companion.getEventBus().post(event);
        floatingItemTimeLeft -= event.getSpeed();
    }
}

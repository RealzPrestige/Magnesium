package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Shadow
    private int floatingItemTimeLeft;

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void getFov(CallbackInfoReturnable<Double> callbackInfoReturnable) {
        ZoomEvent event = new ZoomEvent(callbackInfoReturnable.getReturnValue());
        Main.Companion.getEventBus().invoke(event);
        callbackInfoReturnable.setReturnValue(event.getFov());
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void renderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        Main.Companion.getEventBus().invoke(new Render3DEvent(matrices));
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void bobViewWhenHurt(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo) {
        BobEvent event = new BobEvent();
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;floatingItemTimeLeft:I"))
    private void adjustFloatingTimeLeft(CallbackInfo ci) {
        FloatingItemEvent event = new FloatingItemEvent(0);
        Main.Companion.getEventBus().invoke(event);
        floatingItemTimeLeft -= event.getSpeed();
    }

    @Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"))
    private void translateItem(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo callbackInfo) {
        RenderHandEvent event = new RenderHandEvent(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            matrices.translate(event.getX(), event.getY(), event.getZ());
            matrices.scale(event.getScaleX(), event.getScaleY(), event.getScaleZ());
        }
    }
}

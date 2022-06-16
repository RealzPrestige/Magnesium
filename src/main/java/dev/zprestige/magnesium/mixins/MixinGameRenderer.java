package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.BobEvent;
import dev.zprestige.magnesium.event.impl.Render3DEvent;
import dev.zprestige.magnesium.event.impl.ZoomEvent;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    public void getFov(CallbackInfoReturnable<Double> callbackInfoReturnable) {
        ZoomEvent event = new ZoomEvent(callbackInfoReturnable.getReturnValue());
        Main.Companion.getEventBus().post(event);
        callbackInfoReturnable.setReturnValue(event.getFov());
    }
    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void renderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci){
        Main.Companion.getEventBus().post(new Render3DEvent(matrices));
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void bobViewWhenHurt(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo){
        BobEvent event = new BobEvent();
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()){
            callbackInfo.cancel();
        }
    }
}

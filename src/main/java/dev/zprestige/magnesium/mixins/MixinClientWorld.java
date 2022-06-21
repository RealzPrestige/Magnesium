package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.ColorFogEvent;
import dev.zprestige.magnesium.event.impl.TimeEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Shadow
    @Final
    private ClientWorld.Properties clientWorldProperties;
    @Inject(at = @At("TAIL"), method = "setTimeOfDay", cancellable = true)
    private void setTimeOfDay(long time, CallbackInfo callbackInfo) {
        TimeEvent event = new TimeEvent(time);
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()){
            callbackInfo.cancel();
            return;
        }
        clientWorldProperties.setTimeOfDay(event.getTime());
    }

    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    private void onGetSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Vec3d> info) {
        ColorFogEvent event = new ColorFogEvent(0.0f, 0.0f, 0.0f);
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()) {
            info.setReturnValue(new Vec3d(0.0f, 1.0f, 1.0f));
        }
    }
}

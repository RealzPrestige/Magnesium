package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
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
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()){
            callbackInfo.cancel();
            return;
        }
        clientWorldProperties.setTimeOfDay(event.getTime());
    }

}
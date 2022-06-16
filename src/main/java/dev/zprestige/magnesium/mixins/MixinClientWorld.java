package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.TimeEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Shadow
    @Final
    private ClientWorld.Properties clientWorldProperties;

    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("TAIL"), method = "setTimeOfDay", cancellable = true)
    public void setTimeOfDay(long time, CallbackInfo callbackInfo) {
        TimeEvent event = new TimeEvent(time);
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()){
            callbackInfo.cancel();
            return;
        }
        clientWorldProperties.setTimeOfDay(event.getTime());
    }
}

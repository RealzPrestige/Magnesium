package dev.zprestige.magnesium.mixins

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.event.impl.TickEvent
import net.minecraft.client.MinecraftClient
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(MinecraftClient::class)
class MixinMinecraftClient {

    @Inject(method = ["tick"], at = [At(value = "HEAD")])
    private fun runTick(callbackInfo: CallbackInfo){
        Main.eventBus.post(TickEvent())
    }
}
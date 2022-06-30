package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.ClearChatEvent;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class MixinChatHud {
    @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
    private void clear(boolean clearHistory, CallbackInfo callbackInfo) {
        ClearChatEvent event = new ClearChatEvent();
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }
}

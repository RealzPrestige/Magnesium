package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.AddMessageEvent;
import dev.zprestige.magnesium.event.impl.ClearChatEvent;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Shadow
    protected abstract void addMessage(Text message, int messageId);

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;I)V"), cancellable = true)
    private void addMessage(Text message, CallbackInfo callbackInfo) {
        AddMessageEvent event = new AddMessageEvent(message);
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()) {
            callbackInfo.cancel();
            addMessage(event.getMessage(), 0);
        }
    }

    @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
    private void clear(boolean clearHistory, CallbackInfo callbackInfo) {
        ClearChatEvent event = new ClearChatEvent();
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }
}

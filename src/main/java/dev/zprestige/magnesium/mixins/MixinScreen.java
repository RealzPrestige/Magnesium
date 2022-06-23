package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.BackgroundEvent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class MixinScreen {

    @Inject(method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V", at = @At("HEAD"), cancellable = true)
    private void renderBackground(MatrixStack matrices, int vOffset, CallbackInfo callbackInfo){
        BackgroundEvent event = new BackgroundEvent();
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }
}

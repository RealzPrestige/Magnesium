package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(method = "onKey", at = @At( "HEAD"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci){
        if (key != -1) {
            Main.Companion.getFeatureManager().keyPressed(key);
        }
        System.out.println(key + " " + scancode);
    }

}

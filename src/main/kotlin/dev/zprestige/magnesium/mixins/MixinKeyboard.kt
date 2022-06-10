package dev.zprestige.magnesium.mixins

import dev.zprestige.magnesium.Main
import net.minecraft.client.Keyboard
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(Keyboard::class)
class MixinKeyboard {

    @Inject(method = ["onKey"], at = [At(value = "HEAD")])
    private fun onKey(window: Long, key: Int, scancode: Int, action: Int, modifiers: Int, info: CallbackInfo) {
        Main.featureManager.keyPressed(key)
    }
}
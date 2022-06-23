package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.AnimateHeldItemsEvent;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {

    @ModifyArgs(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lnet/minecraft/util/math/Quaternion;)V"))
    public void modifyPrevRotations(Args args) {
        AnimateHeldItemsEvent event = new AnimateHeldItemsEvent();
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            args.set(0, Vec3f.POSITIVE_X.getDegreesQuaternion(0));
        }
    }
}

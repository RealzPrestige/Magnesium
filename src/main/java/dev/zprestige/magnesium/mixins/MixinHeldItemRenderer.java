package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.AnimateHeldItemsEvent;
import dev.zprestige.magnesium.event.impl.HeldItemsEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Objects;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {

    @Shadow
    private ItemStack mainHand;
    @Shadow
    private ItemStack offHand;
    @Shadow
    private float equipProgressMainHand;
    @Shadow
    private float prevEquipProgressMainHand;
    @Shadow
    private float equipProgressOffHand;
    @Shadow
    private float prevEquipProgressOffHand;

    @Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At("HEAD"))
    private void renderItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo ci){
        HeldItemsEvent event = new HeldItemsEvent();
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()){
            if (prevEquipProgressMainHand >= 0.9f) {
                equipProgressMainHand = 1.0f;
                mainHand = Objects.requireNonNull(Main.Companion.getMc().player).getMainHandStack();
            }
            if (prevEquipProgressOffHand >= 0.9f) {
                equipProgressOffHand = 1.0f;
                offHand = Objects.requireNonNull(Main.Companion.getMc().player).getOffHandStack();
            }
        }
    }

    @ModifyArgs(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lnet/minecraft/util/math/Quaternion;)V"))
    public void modifyPrevRotations(Args args) {
        AnimateHeldItemsEvent event = new AnimateHeldItemsEvent();
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            args.set(0, Vec3f.POSITIVE_X.getDegreesQuaternion(0));
        }
    }
}

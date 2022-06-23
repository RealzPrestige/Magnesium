package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.CrystalEvent;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EndCrystalEntityRenderer.class)
public class MixinEndCrystalEntityRenderer {
    @Mutable
    @Shadow
    @Final
    private static RenderLayer END_CRYSTAL;

    @Shadow
    @Final
    private static Identifier TEXTURE;

    @Shadow
    @Final
    private ModelPart core;

    @Shadow
    @Final
    private ModelPart frame;

    @Inject(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void render(EndCrystalEntity endCrystalEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        CrystalEvent.Texture event = new CrystalEvent.Texture(1.0f, 1.0f, 1.0f, 1.0f);
        Main.Companion.getEventBus().invoke(event);
        END_CRYSTAL = RenderLayer.getEntityTranslucent(event.getCancelled() ? new Identifier("magnesium", "textures/blank.png") : TEXTURE);
    }

    @ModifyArgs(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V", ordinal = 0))
    private void modifyScale(Args args) {
        CrystalEvent.Scale event = new CrystalEvent.Scale(1.0f);
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            args.set(0, 2.0F * event.getScale());
            args.set(1, 2.0F * event.getScale());
            args.set(2, 2.0F * event.getScale());
        }
    }

    @ModifyArgs(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3f;getDegreesQuaternion(F)Lnet/minecraft/util/math/Quaternion;"))
    private void modifySpeed(Args args) {
        CrystalEvent.Rotation event = new CrystalEvent.Rotation(1.0f);
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            args.set(0, ((float) args.get(0)) * event.getRotationSpeed());
        }
    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EndCrystalEntityRenderer;getYOffset(Lnet/minecraft/entity/decoration/EndCrystalEntity;F)F"))
    private float modifyBounce(EndCrystalEntity crystal, float tickDelta) {
        CrystalEvent.Bounce event = new CrystalEvent.Bounce(1.0f);
        Main.Companion.getEventBus().invoke(event);
        float f = crystal.endCrystalAge + tickDelta;
        float g = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        g = (g * g + g) * (0.4F * (event.getCancelled() ? event.getBounceSpeed() : 1.0f));
        return g - 1.4F;
    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 3))
    private void modifyCore(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        CrystalEvent.Texture event = new CrystalEvent.Texture(1.0f, 1.0f, 1.0f, 1.0f);
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()){
            core.render(matrices, vertices, light, overlay, event.getRed(), event.getGreen(), event.getBlue(), event.getAlpha());
        } else {
            core.render(matrices, vertices, light, overlay);
        }

    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 1))
    private void colorFrame1(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        CrystalEvent.Texture event = new CrystalEvent.Texture(1.0f, 1.0f, 1.0f, 1.0f);
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()){
            frame.render(matrices, vertices, light, overlay, event.getRed(), event.getGreen(), event.getBlue(), event.getAlpha());
        } else {
            frame.render(matrices, vertices, light, overlay);
        }
    }

    @Redirect(method = "render(Lnet/minecraft/entity/decoration/EndCrystalEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V", ordinal = 2))
    private void colorFrame2(ModelPart modelPart, MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        CrystalEvent.Texture event = new CrystalEvent.Texture(1.0f, 1.0f, 1.0f, 1.0f);
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()){
            frame.render(matrices, vertices, light, overlay, event.getRed(), event.getGreen(), event.getBlue(), event.getAlpha());
        } else {
            frame.render(matrices, vertices, light, overlay);
        }
    }
}

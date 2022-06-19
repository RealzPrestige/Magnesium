package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.BobEvent;
import dev.zprestige.magnesium.event.impl.FloatingItemEvent;
import dev.zprestige.magnesium.event.impl.Render3DEvent;
import dev.zprestige.magnesium.event.impl.ZoomEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Shadow
    protected abstract void updateFovMultiplier();

    @Final
    @Shadow
    private LightmapTextureManager lightmapTextureManager;

    @Final
    @Shadow
    private Camera camera;

    @Shadow
    private int ticks;

    @Final
    @Shadow
    public HeldItemRenderer firstPersonRenderer;

    @Shadow
    private int floatingItemTimeLeft;

    @Shadow
    @Nullable
    private ItemStack floatingItem;

    @Shadow
    private float skyDarkness;

    @Shadow
    private float lastSkyDarkness;

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    public void getFov(CallbackInfoReturnable<Double> callbackInfoReturnable) {
        ZoomEvent event = new ZoomEvent(callbackInfoReturnable.getReturnValue());
        Main.Companion.getEventBus().post(event);
        callbackInfoReturnable.setReturnValue(event.getFov());
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    private void renderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        Main.Companion.getEventBus().post(new Render3DEvent(matrices));
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void bobViewWhenHurt(MatrixStack matrices, float tickDelta, CallbackInfo callbackInfo) {
        BobEvent event = new BobEvent();
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()) {
            callbackInfo.cancel();
        }
    }

    /**
     * @author zPrestige_
     * @reason vroom totem
     */

    @Overwrite
    public void tick() {
        updateFovMultiplier();
        lightmapTextureManager.tick();
        if (Main.Companion.getMc().getCameraEntity() == null) {
            Main.Companion.getMc().setCameraEntity(Main.Companion.getMc().player);
        }
        camera.updateEyeHeight();
        ++ticks;
        firstPersonRenderer.updateHeldItems();
        Main.Companion.getMc().worldRenderer.tickRainSplashing(camera);
        lastSkyDarkness = skyDarkness;
        if (Main.Companion.getMc().inGameHud.getBossBarHud().shouldDarkenSky()) {
            skyDarkness += 0.05f;
            if (skyDarkness > 1.0f) {
                skyDarkness = 1.0f;
            }
        } else if (skyDarkness > 0.0f) {
            skyDarkness -= 0.0125f;
        }
        if (floatingItemTimeLeft > 0) {
            FloatingItemEvent event = new FloatingItemEvent(1);
            Main.Companion.getEventBus().post(event);
            floatingItemTimeLeft -= event.getSpeed();
            if (floatingItemTimeLeft == 0) {
                floatingItem = null;
            }
        }
    }
}

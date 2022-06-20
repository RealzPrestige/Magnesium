package dev.zprestige.magnesium.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.RenderTerrainFogEvent;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {
    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void onApplyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo callbackInfo) {
        if (fogType == BackgroundRenderer.FogType.FOG_TERRAIN) {
            RenderTerrainFogEvent event = new RenderTerrainFogEvent(0.0f, 0.0f);
            Main.Companion.getEventBus().post(event);
            if (event.getCancelled()) {
                RenderSystem.setShaderFogStart(event.getStart());
                RenderSystem.setShaderFogEnd(event.getEnd());
            }
        }
    }
}
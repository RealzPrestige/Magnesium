package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.Render2DEvent;
import dev.zprestige.magnesium.event.impl.RenderScoreboardEvent;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrixStack, float tickDelta, CallbackInfo callbackInfo) {
        Main.Companion.getEventBus().post(new Render2DEvent(matrixStack, scaledWidth, scaledHeight));
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective, CallbackInfo ci) {
        RenderScoreboardEvent event = new RenderScoreboardEvent();
        Main.Companion.getEventBus().post(event);
        if (event.getCancelled()) {
            ci.cancel();
        }
    }
}
package dev.zprestige.magnesium.mixins;


import dev.zprestige.magnesium.util.RenderUtil;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(ClickableWidget.class)
public class MixinClickableWidget {
    private float scale = 1.0f;
    @Shadow
    protected boolean hovered;
    @Shadow
    public int x;
    @Shadow
    public int y;
    @Shadow
    protected int width;
    @Shadow
    protected int height;


    @Redirect(method = "renderButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ClickableWidget;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
    private void renderButton(ClickableWidget instance, MatrixStack matrixStack, int i, int j, int k, int l, int m, int n) {
        float x = this.x - scale;
        float y = this.y - scale;
        float width = this.width + scale * 2;
        float height = this.height + scale * 2;
        RenderUtil.Companion.fillCorrectly(matrixStack, x + 4, y, x + width - 4, y + height, new Color(0, 0, 0, 30));
        RenderUtil.Companion.fillGradient(matrixStack, x, y, x + 4, y + height, new Color(0, 0, 0, 0), new Color(0, 0, 0, 30), true);
        RenderUtil.Companion.fillGradient(matrixStack, x + width - 4, y, x + width, y + height, new Color(0, 0, 0, 30), new Color(0, 0, 0, 0), true);
        if (hovered){
            if (scale < 1.0f){
                scale += (1.0f - scale) / 20.0f;
            } else {
                scale = 1.0f;
            }
        } else {
            if (scale > 0.0f){
                scale -= (scale / 20.0f);
            } else {
                scale = 0.0f;
            }
        }
    }

}

package dev.zprestige.magnesium.mixins;

import dev.zprestige.magnesium.Main;
import dev.zprestige.magnesium.event.impl.TerrainScreenEvent;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DownloadingTerrainScreen.class)
public class MixinDownloadingTerrainScreen extends Screen {

    protected MixinDownloadingTerrainScreen(Text title) {
        super(title);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo callbackInfo) {
        TerrainScreenEvent event = new TerrainScreenEvent();
        Main.Companion.getEventBus().invoke(event);
        if (event.getCancelled()) {
            close(); 
        }
    }
}

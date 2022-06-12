package dev.zprestige.magnesium.ui.selected.impl

import com.mojang.blaze3d.systems.RenderSystem
import dev.zprestige.magnesium.ui.ClickGui
import dev.zprestige.magnesium.ui.button.settings.impl.othermods.OtherMod
import dev.zprestige.magnesium.ui.selected.Selected
import net.minecraft.client.util.math.MatrixStack
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OtherMods : Selected("Other Mods") {
    private val otherMods: ArrayList<Mod> = ArrayList()
    private val otherModsDrawables = ArrayList<OtherMod>()

    init {
        tab = "All"
        this.buttons = arrayOf(createButton("All", { tab = "All" }, true),
            createButton("Performance", { tab = "Performance" }, false),
            createButton("Extra", { tab = "Extra" }, false)
        )
        otherMods.addAll(arrayOf(
            Mod("Sodium", "https://www.curseforge.com/minecraft/mc-mods/sodium/download/3669187", ModType.Performance),
            Mod("Phosphor", "https://www.curseforge.com/minecraft/mc-mods/phosphor/download", ModType.Performance),
            Mod("Sodium-Extra", "https://www.curseforge.com/minecraft/mc-mods/sodium-extra/download/3821006", ModType.Performance),
            Mod("Starlight", "https://www.curseforge.com/minecraft/mc-mods/starlight/download/3667443", ModType.Performance),
            Mod("Replaymod", "https://www.replaymod.com/download/download_new.php?version=1.18.2-2.6.5", ModType.Extra),
            Mod("ShulkerTooltip", "https://www.curseforge.com/minecraft/mc-mods/shulker-tooltip/download", ModType.Extra),
            Mod("Optifabric", "https://www.curseforge.com/minecraft/mc-mods/optifabric/download", ModType.Performance),
            Mod("Litematica", "https://www.curseforge.com/minecraft/mc-mods/litematica/download", ModType.Extra),
            Mod("Iris", "https://www.curseforge.com/minecraft/mc-mods/irisshaders/download/3820231", ModType.Performance),
            Mod("Lithium", "https://www.curseforge.com/minecraft/mc-mods/lithium/download", ModType.Performance),
            Mod("Indium", "https://www.curseforge.com/minecraft/mc-mods/indium/download/3767308", ModType.Performance),
            Mod("EntityCulling", "https://www.curseforge.com/minecraft/mc-mods/entityculling/download/3743933", ModType.Performance),
            Mod("Continuity", "https://www.curseforge.com/minecraft/mc-mods/continuity/download/3822250", ModType.Performance),
            Mod("3DSkinLayers", "https://www.curseforge.com/minecraft/mc-mods/skin-layers-3d/download/3808405", ModType.Extra),
            Mod("LazyDFU", "https://www.curseforge.com/minecraft/mc-mods/lazydfu/download/3209972", ModType.Performance)
        ))

        otherMods.forEach { otherMod ->
            otherModsDrawables.add(OtherMod(otherMod, 0.0f, 0.0f, 170.0f, 75.0f))
        }
    }

    override fun renderInner(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        if (addY > 0.0f) {
            addY -= addY / 10.0f
        }
        var w = 0.0f
        var deltaY = 0.0f
        val modType = when (tab) {
            "Performance" -> ModType.Performance
            "Extra" -> ModType.Extra
            else -> {
                null
            }
        }
        RenderSystem.enableScissor(0, 294, 100000, 571)
        for (d in otherModsDrawables) {
            if (modType != null && d.mod.modType != modType){
                continue
            }
            if (ClickGui.searching != "") {
                if (!d.mod.modName.lowercase()
                        .contains(ClickGui.searching.lowercase())
                ) {
                    continue
                }
            }
            if (w >= 450.0f) {
                w = 0.0f
                deltaY += 85.0f
            }
            d.x = width / 3.5f + 17.5f + w
            d.y = y + 38.0f + deltaY + addY
            w += 185.1428f
            d.render(matrices,
                mouseX,
                mouseY,
                clickFrame
            )
        }
        RenderSystem.disableScissor()
    }

    inner class Mod(val modName: String, val url: String, val modType: ModType)

    enum class ModType {
        Performance,
        Extra
    }
}
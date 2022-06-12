package dev.zprestige.magnesium.ui

import com.mojang.blaze3d.systems.RenderSystem
import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.Main.Companion.mc
import dev.zprestige.magnesium.ui.selected.Selected
import dev.zprestige.magnesium.ui.selected.impl.Features
import dev.zprestige.magnesium.ui.selected.impl.Hud
import dev.zprestige.magnesium.ui.selected.impl.OtherMods
import dev.zprestige.magnesium.ui.selected.impl.Profiles
import dev.zprestige.magnesium.ui.sidebar.SideTab
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.awt.Color

class ClickGui : Screen(Text.of("ClickGui")) {
    private val width1: Float = 800.0f
    private val height1: Float = 400.0f
    private var clickFrame = false
    private var releaseFrame = false
    private var typingFrame = false
    private var char: Char? = null
    private val sideTabs: ArrayList<SideTab> = ArrayList()
    private val features: SideTab = SideTab("Features")
    private val otherMods: SideTab = SideTab("Other Mods")
    private val hud: SideTab = SideTab("Hud")
    private val profiles: SideTab = SideTab("Profiles")
    private val featureSelected: Selected = Features()
    private val otherModsSelected: Selected = OtherMods()
    private val profilesSelected: Selected = Profiles()
    private var isSearching: Boolean = false
    private var prevTime: Long = 0L
    private var prevSidebar = sidebar

    init {
        sideTabs.addAll(arrayOf(
            features,
            otherMods,
            hud,
            profiles
        )
        )
        sidebar = features
        selected = featureSelected
    }

    companion object {
        var searching: String = ""
        var sidebar: SideTab? = null
        var selected: Selected? = null
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        /** set values by default */
        if (sidebar == null) {
            sidebar = features
        }
        if (selected == null) {
            selected = featureSelected
        }
        /** find x and y */
        val x = (this.width / 2.0f) - (width1 / 2.0f)
        val y = (this.height / 2.0f) - (height1 / 2.0f)

        /** setup our side tabs */
        val tabWidth = (width1 / 3.5f) - x
        var deltaY = y + 30.0f
        sideTabs.forEach { tab ->
            tab.setup(x, deltaY, tabWidth)
            deltaY += 17.0f
        }

        /** main box */
        RenderUtil.fill(matrices, x, y, width1, height1, Color(0, 0, 0, 50))

        /** separate left box with right box */
        RenderUtil.fillGradient(matrices,
            width1 / 3.5f - 0.5f,
            y,
            width1 / 3.5f + 4.5f,
            height1,
            Color(0, 0, 0, 50),
            Color(0, 0, 0, 0),
            true
        )

        /** top box */
        RenderUtil.fill(matrices, width1 / 3.5f + 9.0f, y, width1 - 4.5f, y + 30.0f, Color(0, 0, 0, 40))

        /** top box gradient */
        RenderUtil.fillGradient(matrices,
            width1 / 3.5f + 4.5f,
            y,
            width1 / 3.5f + 8.5f,
            y + 30.0f,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 40),
            true
        )
        RenderUtil.fillGradient(matrices,
            width1 - 5.0f,
            y,
            width1,
            y + 30.0f,
            Color(0, 0, 0, 40),
            Color(0, 0, 0, 0),
            true
        )

        /** left box */
        RenderUtil.fill(matrices, x, y, width1 / 3.5f, height1, Color(0, 0, 0, 50))

        /** top (left) watermark */
        val waterMark = "Magnesium ${Main.version}"
        Main.fontManager.prepare(1.05f)
            .drawStringWithShadow(matrices,
                waterMark,
                x + (((width1 / 3.5f) - x) / 2.0f - (Main.fontManager.getStringWidth(waterMark,
                    1.05f
                ) / 2.0f)),
                y + 4.0f,
                Color.WHITE
            )
            .pop()

        /** box behind name & player head */
        RenderUtil.fill(matrices,
            x + 5.0f,
            height1 - 30.0f,
            width1 / 3.5f - 5.0f,
            height1 - 5.0f,
            Color(255, 255, 255, 30)
        )

        /** depth player name/head box */
        RenderUtil.fillGradient(matrices,
            width1 / 3.5f - 5.5f,
            height1 - 30.0f,
            width1 / 3.5f - 3,
            height1 - 5.0f,
            Color(255, 255, 255, 30),
            Color(255, 255, 255, 0),
            true
        )
        RenderUtil.fillGradient(matrices,
            x + 2.5f,
            height1 - 30.0f,
            x + 5.0f,
            height1 - 5.0f,
            Color(255, 255, 255, 0),
            Color(255, 255, 255, 30),
            true
        )

        /** player name, head and latency */
        if (mc.player != null) {
            Main.fontManager.prepare(0.90f)
                .drawStringWithShadow(matrices,
                    mc.player?.entityName.toString(),
                    x + 35.0f,
                    height1.toInt() - 25.0f,
                    Color.WHITE
                )
                .drawStringWithShadow(matrices,
                    "${mc.networkHandler?.getPlayerListEntry(mc.player?.uuid)?.latency.toString()}ms",
                    x + 35.0f,
                    height1.toInt() - 25.0f + Main.fontManager.getHeight(0.90f),
                    Color.WHITE
                )
                .pop()
            /** player head */
            RenderUtil.drawTexture(matrices,
                mc.player?.skinTexture!!,
                x.toInt() + 10,
                height1.toInt() - (27 + (1 / 2)),
                0,
                20.0f,
                20.0f,
                20,
                20,
                160,
                160
            )

            /** depth player head */
            RenderUtil.fillGradient(matrices,
                x + 30.0f,
                height1 - 27,
                x + 32.0f,
                height1 - 7,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x + 10.0f,
                height1 - 7,
                x + 30.0f,
                height1 - 5,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                false
            )
        }

        /** box search bar */
        RenderUtil.fill(matrices, width1 - 154, y + 5.0f, width1 - 10, y + 25.0f, Color(0, 0, 0, 30))

        /** gradient search box */
        RenderUtil.fillGradient(matrices,
            width1 - 158,
            y + 5.0f,
            width1 - 154,
            y + 25.0f,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 30),
            true
        )
        RenderUtil.fillGradient(matrices,
            width1 - 10.0f,
            y + 5.0f,
            width1 - 6,
            y + 25.0f,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 0),
            true
        )

        /** search value */
        if (typingFrame && isSearching && char != null) {
            searching += char.toString()
        }
        if (searching == "") {
            Main.fontManager.drawStringWithShadow(matrices,
                "Search${typingIcon()}",
                width1 - 154,
                y + 15 - (Main.fontManager.getHeight() / 2.0f),
                Color(150, 150, 150)
            )
        } else {
            RenderSystem.enableScissor(width - 154, (y + 5).toInt(), 770, 20000)
            Main.fontManager.drawStringWithShadow(matrices,
                "$searching${typingIcon()}",
                width1 - 154,
                y + 15 - (Main.fontManager.getHeight() / 2.0f),
                Color.WHITE
            )
            RenderSystem.disableScissor()
        }

        if (clickFrame && mouseX > width1 - 154 && mouseY > y + 5.0f && mouseX < width1 - 10 && mouseY < y + 25.0f) {
            isSearching = !isSearching
        }

        /** selected side tab box */
        RenderUtil.fill(matrices, width1 / 3.5f + 8, y + 34.0f, width1 - 4, height1 - 4, Color(0, 0, 0, 30))

        /** gradient selected box */
        RenderUtil.fillGradient(matrices,
            width1 - 4,
            y + 34.0f,
            width1,
            height1 - 4,
            Color(0, 0, 0, 30),
            Color(0, 0, 0, 0),
            true
        )
        RenderUtil.fillGradient(matrices,
            width1 / 3.5f + 4,
            y + 34.0f,
            width1 / 3.5f + 7.5f,
            height1 - 4,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 30),
            true
        )

        /** draw each sidetab */
        sideTabs.forEach { tab ->
            tab.render(matrices, mouseX, mouseY, clickFrame)
        }

        /** check if sidebar has changed */
        if (sidebar != prevSidebar) {
            /** set selected according to new sidebar value */
            when (sidebar?.name) {
                "Features" -> { selected = featureSelected }
                "Other Mods" -> selected = otherModsSelected
                "Hud" -> mc.setScreen(Hud())
                "Profiles" -> selected = profilesSelected
            }
            selected!!.addY = 5.0f
        }

        /** setup before drawing selected */
        selected?.setup(x, y, width1, height1)

        /** draw selected component */
        selected?.renderButtons(matrices, mouseX, mouseY, clickFrame)
        selected?.renderInner(matrices, mouseX, mouseY, clickFrame, releaseFrame)

        /** save previous sidebar value */
        prevSidebar = sidebar

        /** Reset click/release frames / char */
        clickFrame = false
        releaseFrame = false
        char = null
    }

    /** Mouse button is pressed */
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        clickFrame = true
        return super.mouseClicked(mouseX, mouseY, button)
    }

    /** Mouse button is released */
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        releaseFrame = true
        return super.mouseReleased(mouseX, mouseY, button)
    }

    /** Key is pressed it gives the keycode */
    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == 259 && isSearching && searching.isNotEmpty()) {
            searching = searching.substring(0, searching.length - 1)
        }
        if (keyCode == 257){
            isSearching = false
        }
        typingFrame = true
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    /** When a character is pressed (Fabric doesn't use Keyboard, so we have two separate functions) */
    override fun charTyped(chr: Char, modifiers: Int): Boolean {
        if (chr.code != 167 && chr >= ' ' && chr.code != 127) {
            char = chr
        }
        selected!!.charTyped(chr)
        return super.charTyped(chr, modifiers)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
        selected!!.mouseScrolled(mouseX, mouseY, amount)
        return super.mouseScrolled(mouseX, mouseY, amount)
    }

    /** override the default should pause to false */
    override fun shouldPause(): Boolean {
        return false
    }

    /** returns _ or nothing depending on time */
    private fun typingIcon(): String {
        val time = System.currentTimeMillis()
        if (time - prevTime <= 500 && isSearching) {
            return "_"
        }
        if (time - prevTime >= 1000) {
            prevTime = time
        }
        return ""
    }
}
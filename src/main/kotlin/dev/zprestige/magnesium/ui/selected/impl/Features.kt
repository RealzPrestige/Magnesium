package dev.zprestige.magnesium.ui.selected.impl

import com.mojang.blaze3d.systems.RenderSystem
import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.settings.Setting
import dev.zprestige.magnesium.settings.impl.*
import dev.zprestige.magnesium.ui.ClickGui
import dev.zprestige.magnesium.ui.button.Button
import dev.zprestige.magnesium.ui.button.settings.SettingButton
import dev.zprestige.magnesium.ui.button.settings.impl.feature.FeatureEnabled
import dev.zprestige.magnesium.ui.button.settings.impl.feature.FeatureKeybind
import dev.zprestige.magnesium.ui.button.settings.impl.selected.*
import dev.zprestige.magnesium.ui.selected.Selected
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color
import java.util.*

class Features : Selected("Features") {
    private val features: ArrayList<Feature> = Main.featureManager.features
    private val featureListComponents: ArrayList<FeatureListComponent> = ArrayList()
    private var selectedFeature: SelectedFeature? = null
    private val selectedFeatures: ArrayList<SelectedFeature> = ArrayList()

    companion object {
        private val performance: Performance = Performance(0.0f, 0.0f, 0.0f, 0.0f)
    }

    private val keybinds: Keybinds = Keybinds(0.0f, 0.0f, 0.0f, 0.0f)
    private var scroll = 0.0f

    init {
        /** set tab by default */
        tab = "General"
        /** create all buttons */
        this.buttons = arrayOf(
            createButton("General", { tab = "General" }, true),
            createButton("Performance", { tab = "Performance" }, false),
            createButton("Keybinds", { tab = "Keybinds" }, false)
        )
        /** initialize all features into featurelistcomponents with incorrect x and y */
        for (f in features) {
            featureListComponents.add(FeatureListComponent(f, 0.0f, 0.0f, 170.0f, 75.0f))
            selectedFeatures.add(SelectedFeature(f, 0.0f, 0.0f, width - 4, height - 0.4f))
        }
        selectedFeature = null
    }

    /** render inside the big box */
    override fun renderInner(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
        when (tab) {
            "General" -> {
                if (selectedFeature == null) {
                    if (addY > 0.0f) {
                        addY -= addY / 10.0f
                    }
                    var w = 0.0f
                    var deltaY = scroll
                    RenderSystem.enableScissor(0, 294, 100000, 571)
                    for (f in featureListComponents) {
                        if (ClickGui.searching != "") {
                            if (!f.feature.name.lowercase()
                                    .contains(ClickGui.searching.lowercase())
                            ) {
                                continue
                            }
                        }
                        if (w >= 450.0f) {
                            w = 0.0f
                            deltaY += 85.0f
                        }
                        f.x = width / 3.5f + 17.5f + w
                        f.y = y + 38.0f + deltaY + addY
                        w += 185.1428f
                        f.render(matrices,
                            mouseX,
                            mouseY,
                            clickFrame,
                            releaseFrame
                        )
                    }
                    RenderSystem.disableScissor()
                } else {
                    val selectedFeature: SelectedFeature = selectedFeature!!
                    selectedFeature.setup(width / 3.5f + 8,
                        y + 34.0f,
                        (width - 4) - (width / 3.5f + 8),
                        (height - 4) - (y + 34.0f)
                    )
                    selectedFeature.render(matrices, mouseX, mouseY, clickFrame, releaseFrame)
                }
            }
            "Performance" -> {
                performance.setup(width / 3.5f + 8,
                    y + 34.0f,
                    (width - 4) - (width / 3.5f + 8),
                    (height - 4) - (y + 34.0f)
                )
                performance.render(matrices, mouseX, mouseY, clickFrame, releaseFrame)
            }
            "Keybinds" -> {
                keybinds.setup(width / 3.5f + 8,
                    y + 34.0f,
                    (width - 4) - (width / 3.5f + 8),
                    (height - 4) - (y + 34.0f)
                )
                keybinds.render(matrices, mouseX, mouseY, clickFrame, releaseFrame)
            }
        }
        if (tab != "General") {
            addY = 5.0f
            selectedFeature = null
        }
    }

    fun selectedFeatureByFeature(feature: Feature): SelectedFeature? {
        return selectedFeatures.firstOrNull { it.feature == feature }
    }

    override fun charTyped(chr: Char) {
        when (tab) {
            "General" -> {
                if (selectedFeature != null) {
                    selectedFeature!!.charTyped(chr)
                }
            }
            "Performance" -> {
                performance.charTyped(chr)
            }
            "Keybinds" -> {
                keybinds.charTyped(chr)
            }
        }
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double) {
        when (tab) {
            "General" -> {
                if (selectedFeature != null) {
                    selectedFeature!!.mouseScrolled(mouseX, mouseY, amount)
                } else {
                    if (mouseX > width / 3.5f + 8 && mouseY > y + 34.0f && mouseX < width - 4 && mouseY < height - 4) {
                        scroll += amount.toFloat() * 5
                    }
                }
            }
            "Performance" -> {
                performance.mouseScrolled(mouseX, mouseY, amount)
            }
            "Keybinds" -> {
                keybinds.mouseScrolled(mouseX, mouseY, amount)
            }
        }
    }

    /** feature inside big box when all are listed */
    inner class FeatureListComponent(val feature: Feature, var x: Float, var y: Float, private val width: Float, private val height: Float) {
        private val featureEnabled: FeatureEnabled =
            FeatureEnabled(feature, x + width - 20, y + height - 16 + addY, 15.0f, 15.0f)
        private val featureKeybind: FeatureKeybind =
            FeatureKeybind(feature.keybind, x + 4, y + height - 16 + addY, 30.0f, 15.0f)

        fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
            featureEnabled.setup(x + width - 20, y + height - 16 + addY, 15.0f, 15.0f)
            featureKeybind.setup(x + 4, y + height - 16 + addY, 30.0f, 15.0f)
            /** main box */
            RenderUtil.fill(matrices, x, y, x + width, y + height, Color(0, 0, 0, 30))
            /** gradients on side of box */
            RenderUtil.fillGradient(matrices,
                x + width - 0.25f,
                y,
                x + width + 4,
                y + height,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x - 4,
                y,
                x - 0.25f,
                y + height,
                Color(0, 0, 0, 0),
                Color(0, 0, 0, 30),
                true
            )
            /** draw the name */
            Main.fontManager.drawStringWithShadow(matrices,
                feature.name,
                x + (width / 2.0f) - (Main.fontManager.getStringWidth(feature.name) / 2.0f),
                y + 5.0f,
                Color.WHITE
            )
            /** get the text and separate them when too wide */
            var dy = 0.0f
            var wi = 0.0f
            var text = ""
            val map: HashMap<String, Float> = HashMap()
            for (word in feature.description.split(" ")) {
                wi += Main.fontManager.getStringWidth("$word ", 0.8f)
                text = "$text $word"
                if (wi >= 150.0f) {
                    map[text] = dy
                    text = ""
                    wi = 0.0f
                    dy += Main.fontManager.getHeight(0.8f)
                }
            }
            /** iterate through map of words */
            for (entry in map.entries) {
                Main.fontManager.prepare(0.8f).drawStringWithShadow(matrices,
                    entry.key,
                    x + (width / 2.0f) - (Main.fontManager.getStringWidth(entry.key, 0.8f) / 2.0f),
                    y + Main.fontManager.getHeight(0.8f) + entry.value + 10.0f,
                    Color(200, 200, 200)
                ).pop()
            }
            /** draw the last set of words */
            Main.fontManager.prepare(0.8f).drawStringWithShadow(matrices,
                text,
                x + (width / 2.0f) - (Main.fontManager.getStringWidth(text, 0.8f) / 2.0f),
                y + Main.fontManager.getHeight(0.8f) + dy + 10.0f,
                Color(200, 200, 200)
            ).pop()
            featureEnabled.render(matrices, mouseX, mouseY, clickFrame, releaseFrame)
            featureKeybind.render(matrices, mouseX, mouseY, clickFrame, releaseFrame)
            if (clickFrame && mouseY > this@Features.y + 34.0f && mouseY < this@Features.height - 4.0f  && hovering(mouseX, mouseY) && !featureEnabled.hovering(mouseX,
                    mouseY
                ) && !featureKeybind.hovering(mouseX, mouseY)
            ) {
                selectedFeature = selectedFeatureByFeature(feature)
            }
        }

        private fun hovering(mouseX: Int, mouseY: Int): Boolean {
            return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height
        }
    }

    /** specific feature selected */
    inner class SelectedFeature(val feature: Feature, private var x: Float, private var y: Float, private var width: Float, private var height: Float) {
        private val back: Button = createButton("Back", {
            tab = "General"
            selectedFeature = null
            addY = 5.0f
        }, false)
        private var settingButtons: ArrayList<SettingButton> = ArrayList()
        private var scroll = 0.0f

        init {
            for (it in feature.settings) {
                if (it is Switch && it != feature.enabled) {
                    settingButtons.add(SwitchButton(it, x, y, width, 15.0f))
                }
                if (it is SliderInt) {
                    settingButtons.add(SliderIntButton(it, x, y, width, 15.0f))
                }
                if (it is SliderFloat) {
                    settingButtons.add(SliderFloatButton(it, x, y, width, 15.0f))
                }
                if (it is Keybind) {
                    settingButtons.add(KeybindButton(it, x, y, width, 15.0f))
                }
                if (it is Combo) {
                    settingButtons.add(ComboButton(it, x, y, width, 15.0f))
                }
                if (it is ColorBox) {
                    settingButtons.add(ColorBoxButton(it, x, y, width, 15.0f))
                }
            }
        }

        fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
            back.setup(x + width - 107.25f, y + 5.5f, 100.0f, 13.0f)
            back.render(matrices, mouseX, mouseY, clickFrame)
            RenderUtil.fill(matrices, x + 4, y + 4, x + width - 4, y + 19.0f, Color(0, 0, 0, 30))
            RenderUtil.fillGradient(matrices,
                x + width - 4,
                y + 4,
                x + width,
                y + 19.0f,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x,
                y + 4,
                x + 3.5f,
                y + 19.0f,
                Color(0, 0, 0, 0),
                Color(0, 0, 0, 30),
                true
            )
            RenderUtil.fill(matrices, x + 4, y + 23, x + width - 4, y + height - 4, Color(0, 0, 0, 30))
            RenderUtil.fillGradient(matrices,
                x + width - 4,
                y + 23.0f,
                x + width,
                y + height - 4,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x,
                y + 23.0f,
                x + 3.5f,
                y + height - 4,
                Color(0, 0, 0, 0),
                Color(0, 0, 0, 30),
                true
            )
            Main.fontManager.prepare(1.5f)
                .drawStringWithShadow(matrices, feature.name, x + 6, y + 15 - Main.fontManager.getHeight(), Color.WHITE)
                .pop()
            Main.fontManager.prepare(0.8f).drawStringWithShadow(matrices,
                feature.description,
                x + 10 + Main.fontManager.getStringWidth(feature.name, 1.5f),
                y + 15 - Main.fontManager.getHeight(0.5f),
                Color(200, 200, 200)
            ).pop()
            RenderSystem.enableScissor(0, 294, 100000, 533)
            var deltaY = y + 27.0f + scroll
            val tabMap: HashMap<String, Float> = HashMap()
            val rendered: ArrayList<SettingButton> = ArrayList()
            for (settingButton in settingButtons) {
                if (!rendered.contains(settingButton)) {
                    if (!tabMap.containsKey(settingButton.tab())) {
                        tabMap[settingButton.tab()] = deltaY
                        deltaY += 17.0f
                        settingButtons.forEach { settingButton2 ->
                            if (settingButton2.tab() == settingButton.tab()) {
                                settingButton2.setup(x + 12.0f, deltaY, width - 24, 15.0f)
                                settingButton2.render(matrices, mouseX, mouseY, mouseY > this@Features.y + 34.0f && mouseY < this@Features.height - 4.0 && clickFrame, releaseFrame)
                                rendered.add(settingButton2)
                                deltaY += if (settingButton2 is ColorBoxButton && settingButton2.opened) {
                                    67.0f
                                } else {
                                    17.0f
                                }
                            }
                        }
                    }
                }
            }
            for (tab in tabMap.entries) {
                Main.fontManager.drawStringWithShadow(matrices,
                    tab.key,
                    x + 8.0f,
                    tab.value + 7.5f - (Main.fontManager.getHeight() / 2.0f),
                    Color.WHITE
                )
            }
            RenderSystem.disableScissor()
        }

        fun charTyped(chr: Char) {
            for (settingButton in settingButtons) {
                settingButton.charTyped(chr)
            }
        }

        fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double) {
            if (mouseX > x + 4 && mouseY > y + 23 && mouseX < x + width - 4 && mouseY < y + height - 4) {
                scroll += amount.toFloat() * 5
            }
        }

        fun setup(x: Float, y: Float, width: Float, height: Float) {
            this.x = x
            this.y = y
            this.width = width
            this.height = height
        }
    }

    class Performance(private var x: Float, private var y: Float, private var width: Float, private var height: Float) {
        private val settings: ArrayList<Setting<*>> = ArrayList()
        //private val chunkLoadingInterval = SliderFloat("Chunk Loading Interval", 0.0f, 0.0f, 300.0f).tab("Chunk")
        private var settingButtons: ArrayList<SettingButton> = ArrayList()
        private var scroll = 0.0f

        init {
            //settings.addAll(arrayOf(chunkLoadingInterval))
            for (it in settings) {
                if (it is Switch) {
                    settingButtons.add(SwitchButton(it, x, y, width, 15.0f))
                }
                if (it is SliderInt) {
                    settingButtons.add(SliderIntButton(it, x, y, width, 15.0f))
                }
                if (it is SliderFloat) {
                    settingButtons.add(SliderFloatButton(it, x, y, width, 15.0f))
                }
                if (it is Keybind) {
                    settingButtons.add(KeybindButton(it, x, y, width, 15.0f))
                }
                if (it is Combo) {
                    settingButtons.add(ComboButton(it, x, y, width, 15.0f))
                }
                if (it is ColorBox) {
                    settingButtons.add(ColorBoxButton(it, x, y, width, 15.0f))
                }
            }
        }

        fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
            RenderUtil.fill(matrices, x + 4, y + 4, x + width - 4, y + 19.0f, Color(0, 0, 0, 30))
            RenderUtil.fillGradient(matrices,
                x + width - 4,
                y + 4,
                x + width,
                y + 19.0f,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x,
                y + 4,
                x + 3.5f,
                y + 19.0f,
                Color(0, 0, 0, 0),
                Color(0, 0, 0, 30),
                true
            )
            RenderUtil.fill(matrices, x + 4, y + 23, x + width - 4, y + height - 4, Color(0, 0, 0, 30))
            RenderUtil.fillGradient(matrices,
                x + width - 4,
                y + 23.0f,
                x + width,
                y + height - 4,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x,
                y + 23.0f,
                x + 3.5f,
                y + height - 4,
                Color(0, 0, 0, 0),
                Color(0, 0, 0, 30),
                true
            )
            Main.fontManager.prepare(1.5f)
                .drawStringWithShadow(matrices,
                    "Performance",
                    x + 6,
                    y + 15 - Main.fontManager.getHeight(),
                    Color.WHITE
                )
                .pop()
            RenderSystem.enableScissor(0, 294, 100000, 533)
            var deltaY = y + 27.0f + scroll
            val tabMap: HashMap<String, Float> = HashMap()
            val rendered: ArrayList<SettingButton> = ArrayList()
            for (settingButton in settingButtons) {
                if (!rendered.contains(settingButton)) {
                    if (!tabMap.containsKey(settingButton.tab())) {
                        tabMap[settingButton.tab()] = deltaY
                        deltaY += 17.0f
                        settingButtons.forEach { settingButton2 ->
                            if (settingButton2.tab() == settingButton.tab()) {
                                settingButton2.setup(x + 12.0f, deltaY, width - 24, 15.0f)
                                settingButton2.render(matrices, mouseX, mouseY, mouseY > y && mouseY < y + height && clickFrame, releaseFrame)
                                rendered.add(settingButton2)
                                deltaY += if (settingButton2 is ColorBoxButton && settingButton2.opened) {
                                    67.0f
                                } else {
                                    17.0f
                                }
                            }
                        }
                    }
                }
            }
            for (tab in tabMap.entries) {
                Main.fontManager.drawStringWithShadow(matrices,
                    tab.key,
                    x + 8.0f,
                    tab.value + 7.5f - (Main.fontManager.getHeight() / 2.0f),
                    Color.WHITE
                )
            }
            RenderSystem.disableScissor()
        }

        fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double) {
            if (mouseX > x + 4 && mouseY > y + 23 && mouseX < x + width - 4 && mouseY < y + height - 4) {
                scroll += amount.toFloat() * 5
            }
        }

        fun charTyped(chr: Char) {
            for (s in settingButtons) {
                s.charTyped(chr)
            }
        }

        fun setup(x: Float, y: Float, width: Float, height: Float) {
            this.x = x
            this.y = y
            this.width = width
            this.height = height
        }
    }

    inner class Keybinds(private var x: Float, private var y: Float, private var width: Float, private var height: Float) {
        private val settings: HashMap<Keybind, Feature> = HashMap()
        private var settingButtons: HashMap<SettingButton, Feature> = HashMap()
        private var scroll = 0.0f

        init {
            features.forEach { f ->
                f.settings.forEach { s ->
                    if (s is Keybind) {
                        settings[s] = f
                    }
                }
            }
            settings.forEach {
                settingButtons[KeybindButton(it.key, x, y, width, 15.0f)] = it.value
            }
        }

        fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean, releaseFrame: Boolean) {
            RenderUtil.fill(matrices, x + 4, y + 4, x + width - 4, y + 19.0f, Color(0, 0, 0, 30))
            RenderUtil.fillGradient(matrices,
                x + width - 4,
                y + 4,
                x + width,
                y + 19.0f,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x,
                y + 4,
                x + 3.5f,
                y + 19.0f,
                Color(0, 0, 0, 0),
                Color(0, 0, 0, 30),
                true
            )
            RenderUtil.fill(matrices, x + 4, y + 23, x + width - 4, y + height - 4, Color(0, 0, 0, 30))
            RenderUtil.fillGradient(matrices,
                x + width - 4,
                y + 23.0f,
                x + width,
                y + height - 4,
                Color(0, 0, 0, 30),
                Color(0, 0, 0, 0),
                true
            )
            RenderUtil.fillGradient(matrices,
                x,
                y + 23.0f,
                x + 3.5f,
                y + height - 4,
                Color(0, 0, 0, 0),
                Color(0, 0, 0, 30),
                true
            )
            Main.fontManager.prepare(1.5f)
                .drawStringWithShadow(matrices,
                    "Keybinds",
                    x + 6,
                    y + 15 - Main.fontManager.getHeight(),
                    Color.WHITE
                )
                .pop()
            RenderSystem.enableScissor(0, 294, 100000, 533)
            var deltaY = y + 27.0f + scroll
            val tabMap: HashMap<Feature, Float> = HashMap()
            val rendered: ArrayList<SettingButton> = ArrayList()
            for (s in settingButtons) {
                if (!rendered.contains(s.key)) {
                    if (!tabMap.containsKey(s.value)) {
                        tabMap[s.value] = deltaY
                        deltaY += 17.0f
                        settingButtons.forEach { s2 ->
                            if (s2.value == s.value) {
                                s2.key.setup(x + 12.0f, deltaY, width - 24, 15.0f)
                                s2.key.render(matrices, mouseX, mouseY,  mouseY > y + 23 &&mouseY < y + height - 4 && clickFrame, releaseFrame)
                                rendered.add(s2.key)
                                deltaY += 17.0f
                            }
                        }
                    }
                }
            }
            for (tab in tabMap.entries) {
                Main.fontManager.drawStringWithShadow(matrices,
                    tab.key.name,
                    x + 8.0f,
                    tab.value + 7.5f - (Main.fontManager.getHeight() / 2.0f),
                    Color.WHITE
                )
            }
            RenderSystem.disableScissor()
        }

        fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double) {
            if (mouseX > x + 4 && mouseY > y + 23 && mouseX < x + width - 4 && mouseY < y + height - 4) {
                scroll += amount.toFloat() * 5
            }
        }

        fun charTyped(chr: Char) {
            for (s in settingButtons) {
                s.key.charTyped(chr)
            }
        }

        fun setup(x: Float, y: Float, width: Float, height: Float) {
            this.x = x
            this.y = y
            this.width = width
            this.height = height
        }
    }
}
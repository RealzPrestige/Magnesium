package dev.zprestige.magnesium.ui.selected.impl

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.ui.selected.Selected
import dev.zprestige.magnesium.util.RenderUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

class Features : Selected("Features") {
    private val features: ArrayList<Feature> = Main.featureManager.features
    private val featureListComponents: ArrayList<FeatureListComponent> = ArrayList()

    init {
        /** set tab by default */
        tab = "General"
        /** create all buttons */
        this.buttons = arrayOf(
            createButton("General") { tab = "General" },
            createButton("Performance") { tab = "Performance" },
            createButton("Keybinds") { tab = "Keybinds" }
        )
        /** initialize all features into featurelistcomponents with incorrect x and y */
        for (f in features) {
            featureListComponents.add(FeatureListComponent(f, 0.0f, 0.0f, 170.0f, 75.0f))
        }
    }

    /** render inside the big box */
    override fun renderInner(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {
        when (tab) {
            "General" -> {
                var w = 0.0f
                var deltaY = 0.0f
                for (f in featureListComponents) {
                    if (w >= 450.0f) {
                        w = 0.0f
                        deltaY += 85.0f
                    }
                    f.x = width / 3.5f + 17.5f + w
                    f.y = y + 38.0f + deltaY
                    w += 185.1428f
                }
                featureListComponents.forEach { featureListComponent ->
                    featureListComponent.render(matrices,
                        mouseX,
                        mouseY,
                        clickFrame
                    )
                }
            }
        }
    }

    /** feature inside big box when all are listed */
    class FeatureListComponent(private val feature: Feature, var x: Float, var y: Float, private val width: Float, private val height: Float) {

        fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {
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
                if (wi >= 160.0f) {
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
        }
    }

    /** specific feature selected */
    class SelectedFeature(private val feature: Feature, private val x: Float, private val y: Float, private val width: Float, private val height: Float) {

        fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, clickFrame: Boolean) {

        }
    }
}
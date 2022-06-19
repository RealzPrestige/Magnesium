package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.settings.Setting
import dev.zprestige.magnesium.settings.impl.*
import java.awt.Color
import java.io.File

class ConfigManager {
    private val separator = File.separator
    private val dir: FileManager.FileObject =
        Main.fileManager.FileObject(File("${Main.mc.runDirectory}${separator}Magnesium")).createDir()
    private val configFile: FileManager.FileObject =
        Main.fileManager.FileObject(File("${dir.file}${separator}Config.txt")).createFile()

    fun save() {
        val writer = configFile.createWriter()
        Main.featureManager.features.forEach { f ->
            if (f.hudComponent != null) {
                writer.write("${f.name} HudComponent ${f.hudComponent!!.x} ${f.hudComponent!!.y}")
            }
            f.settings.forEach {
                val fname = f.name.replace(" ", "_")
                val name = it.name.replace(" ", "_")
                when (it) {
                    is ColorBox -> {
                        writer.write("$fname $name ${it.value.red},${it.value.green},${it.value.blue},${it.value.alpha}")
                    }
                    is Keybind -> {
                        writer.write("$fname $name ${it.value} ${it.hold}")
                    }
                    else -> {
                        writer.write("$fname $name ${it.value}")
                    }
                }
            }
        }
        writer.close()
    }

    fun load() {
        val reader = configFile.createReader()
        reader.lines().forEach { line ->
            val split = line.split(" ")
            val feature = featureByName(split[0].replace("_", " ")) ?: return@forEach
            if (split[1] == "HudComponent") {
                val x = split[2].toFloat()
                val y = split[3].toFloat()
                feature.hudComponent!!.x = x
                feature.hudComponent!!.y = y
                return@forEach
            }
            val setting = settingByName(feature, split[1].replace("_", " ")) ?: return@forEach
            var value = ""
            var k = 0
            val t = feature.name.length + setting.name.length + 1
            for (char in line.toCharArray()) {
                if (k <= t) {
                    k++
                } else {
                    value += char
                }
            }
            if (setting.name == "Enabled") {
                if (value.toBoolean()) {
                    feature.toggle()
                }
                return@forEach
            }
            when (setting) {
                is ColorBox -> {
                    val splitValue = value.split(",")
                    setting.value = Color(splitValue[0].toInt(),
                        splitValue[1].toInt(),
                        splitValue[2].toInt(),
                        splitValue[3].toInt()
                    )
                }
                is Combo -> {
                    setting.value = value
                }
                is Keybind -> {
                    setting.value = value.split(" ")[0].toInt()
                    setting.hold = value.split(" ")[1].toBoolean()
                }
                is SliderFloat -> {
                    setting.value = value.toFloat()
                }
                is SliderInt -> {
                    setting.value = value.toInt()
                }
                is Switch -> {
                    setting.value = value.toBoolean()
                }
            }
        }
        reader.close()
    }

    private fun featureByName(string: String): Feature? {
        return Main.featureManager.features.firstOrNull { it.name == string }
    }

    private fun settingByName(feature: Feature?, string: String): Setting<*>? {
        return feature!!.settings.firstOrNull { it.name == string }
    }
}
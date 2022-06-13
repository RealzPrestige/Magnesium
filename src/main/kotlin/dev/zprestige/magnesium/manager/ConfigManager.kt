package dev.zprestige.magnesium.manager

import dev.zprestige.magnesium.Main
import dev.zprestige.magnesium.features.Feature
import dev.zprestige.magnesium.settings.Setting
import dev.zprestige.magnesium.settings.impl.*
import java.awt.Color
import java.io.File

class ConfigManager {
    private val separator = File.separator
    private val dir: FileManager.FileObject = Main.fileManager.FileObject(File("${Main.mc.runDirectory}${separator}Magnesium")).createDir()
    private val configFile: FileManager.FileObject = Main.fileManager.FileObject(File("${dir.file}${separator}Config.txt")).createFile()

    fun save(){
        val writer = configFile.createWriter()
        Main.featureManager.features.forEach { f ->
            f.settings.forEach {
                if (it is ColorBox){
                    writer.write("${f.name} ${it.name} ${it.value.red},${it.value.green},${it.value.blue},${it.value.alpha}")
                } else {
                    writer.write("${f.name} ${it.name} ${it.value}")
                }
            }
        }
        writer.close()
    }

    fun load(){
        val reader = configFile.createReader()
        reader.lines().forEach { line ->
            val split = line.split(" ")
            val feature = featureByName(split[0])
            val setting = settingByName(feature, split[1])
            val value1 = line.replace(split[0], "").replace(split[1], "")
            var value = ""
            var i = 0
            value1.toCharArray().forEach {
                if (i > 1){
                    value += it
                }
                i += 1
            }

            if (setting?.name.equals("Enabled")){
                if (value.toBoolean()){
                    feature?.toggle()
                }
                return@forEach
            }
            when (setting){
                is ColorBox -> {
                    val splitValue = value.split(",")
                    setting.value = Color(splitValue[0].toInt(), splitValue[1].toInt(), splitValue[2].toInt(), splitValue[3].toInt())
                }
                is Combo -> {
                    setting.value = value
                }
                is Keybind -> {
                    setting.value = value.toInt()
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
package dev.zprestige.magnesium

import dev.zprestige.magnesium.event.eventbus.EventBus
import dev.zprestige.magnesium.event.impl.TickEvent
import dev.zprestige.magnesium.manager.*
import net.fabricmc.api.ModInitializer
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Main : ModInitializer {

    override fun onInitialize() {
        configManager.load()
        Logger.info("Started initializing")
        Runtime.getRuntime().addShutdownHook(Thread {
            configManager.save()
        })
    }


    companion object {
        const val version: Float = 1.0f
        val mc: MinecraftClient = MinecraftClient.getInstance()
        val Logger: Logger = LoggerFactory.getLogger("Magnesium")
        val eventBus: EventBus = EventBus()
        val blurManager: BlurManager = BlurManager()
        val featureManager: FeatureManager = FeatureManager()
        val fontManager: FontManager = FontManager()
        val hudManager: HudManager = HudManager()
        val fileManager: FileManager = FileManager()
        val configManager: ConfigManager = ConfigManager()
        val keyManager: KeyManager = KeyManager()

    }
}
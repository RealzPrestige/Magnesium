package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event
import net.minecraft.client.util.math.MatrixStack

class Render2DEvent(val matrixStack: MatrixStack, val scaledWidth: Int, val scaledHeight: Int) : Event()
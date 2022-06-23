package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event
import net.minecraft.client.util.math.MatrixStack

class Render3DEvent(val matrixStack: MatrixStack) : Event()
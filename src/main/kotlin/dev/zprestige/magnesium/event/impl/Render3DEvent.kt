package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Matrix4f

class Render3DEvent( val matrixStack: MatrixStack) : Event(false)
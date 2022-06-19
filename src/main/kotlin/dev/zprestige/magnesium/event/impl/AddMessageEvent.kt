package dev.zprestige.magnesium.event.impl

import dev.zprestige.magnesium.event.eventbus.Event
import net.minecraft.text.Text

class AddMessageEvent(var message: Text) : Event(true)
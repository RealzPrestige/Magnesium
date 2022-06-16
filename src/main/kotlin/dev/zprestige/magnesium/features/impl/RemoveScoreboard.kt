package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.listener
import dev.zprestige.magnesium.event.impl.RenderScoreboardEvent
import dev.zprestige.magnesium.features.Feature

class RemoveScoreboard : Feature("Remove Scoreboard", "Removes the scoreboard") {

    @EventListener
    fun scoreboardListener() = listener<RenderScoreboardEvent> {
        it.cancelled = true
    }
}
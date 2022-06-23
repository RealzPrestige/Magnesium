package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.Listener
import dev.zprestige.magnesium.event.eventbus.registerListener
import dev.zprestige.magnesium.event.impl.RenderScoreboardEvent
import dev.zprestige.magnesium.features.Feature

class RemoveScoreboard : Feature("Remove Scoreboard", "Removes the scoreboard") {

    @Listener
    fun scoreboardListener() = registerListener<RenderScoreboardEvent> {
        it.cancelled = true
    }
}
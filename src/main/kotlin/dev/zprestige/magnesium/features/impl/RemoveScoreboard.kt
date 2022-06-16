package dev.zprestige.magnesium.features.impl

import dev.zprestige.magnesium.event.eventbus.EventListener
import dev.zprestige.magnesium.event.eventbus.eventListener
import dev.zprestige.magnesium.event.impl.RenderScoreboardEvent
import dev.zprestige.magnesium.features.Feature

class RemoveScoreboard : Feature("Remove Scoreboard", "Removes the scoreboard") {

    @EventListener
    fun scoreboardListener() = eventListener<RenderScoreboardEvent> {
        it.cancel()
    }
}
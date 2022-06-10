package dev.zprestige.magnesium.event.eventbus

abstract class Event(private val cancellable: Boolean) {

    var cancelled = false
        set(value) {
            if (cancellable) field = value
        }

    fun cancel() {
        cancelled = true
    }
}

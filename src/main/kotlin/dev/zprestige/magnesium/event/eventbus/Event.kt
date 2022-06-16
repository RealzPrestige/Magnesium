package dev.zprestige.magnesium.event.eventbus

abstract class Event(private val cancellable: Boolean) {

    var cancelled = false

    fun cancel() {
        if (!cancellable){
            throw Exception("Event annotated as NON-Cancellable, exception thrown!")
        }
        cancelled = true
    }
}

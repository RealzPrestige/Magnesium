package dev.zprestige.magnesium.event.eventbus

import dev.zprestige.magnesium.Main
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.typeOf

internal val <T : Any> KClass<T>.allMembers
    get() = (declaredMembers + allSuperclasses.flatMap { it.declaredMembers }).asSequence()

internal fun <R> KCallable<R>.handleCall(receiver: Any? = null): R {
    isAccessible = true
    return runCatching { call(receiver) }.getOrElse { call() }
}

@Suppress("UNCHECKED_CAST")
private inline val KClass<*>.listeners
    get() = allMembers.filter {
        it.returnType.withNullability(false) == typeOf<Listener>() && it.valueParameters.isEmpty()
    } as Sequence<KCallable<Listener>>

internal fun getListeners(subscriber: Any, config: Config) = runCatching {
    subscriber::class.listeners.filter { !config.annotationRequired || it.hasAnnotation<EventListener>() }
        .map { member -> member.handleCall(subscriber).also { it.subscriber = subscriber } }.toList()
}.onFailure { Main.Logger.error("Unable to register listeners for subscriber $subscriber", it) }.getOrNull()

annotation class EventListener

package dev.zprestige.magnesium.event.eventbus

import dev.zprestige.magnesium.Main
import sun.misc.Unsafe
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.withNullability
import kotlin.reflect.jvm.javaField
import kotlin.reflect.typeOf

internal fun interface CancelledState {

    fun isCancelled(event: Any): Boolean

    companion object {
        private val UNSAFE = runCatching {
            Unsafe::class.declaredMembers.single { it.name == "theUnsafe" }.handleCall() as Unsafe
        }.onFailure {
            Main.Logger.error("Could not obtain Unsafe instance. Will not be able to determine external cancel state.")
        }.getOrNull()
        private val NAMES = arrayOf("canceled", "cancelled")
        private val NOT_CANCELLABLE = CancelledState { false }
        private val CACHE = ConcurrentHashMap<KClass<*>, CancelledState>()


        operator fun invoke(type: KClass<*>): CancelledState = CACHE.getOrPut(type) {
            if (type.isSubclassOf(Event::class)) CancelledState { (it as Event).cancelled }
            else type.allMembers.filter { it.name in NAMES && it.returnType.withNullability(false) == typeOf<Boolean>() }
                .filterIsInstance<KMutableProperty<*>>().filter { it.javaField != null }.toList().let {
                    if (it.isEmpty() || UNSAFE == null) NOT_CANCELLABLE else {
                        if (it.size != 1) Main.Logger.warn("Multiple possible cancel fields found for event $type")
                        val offset = it[0].javaField!!.let { field ->
                            if (Modifier.isStatic(field.modifiers))
                                UNSAFE.staticFieldOffset(field)
                            else UNSAFE.objectFieldOffset(field)
                        }
                        CancelledState { event -> UNSAFE.getBoolean(event, offset) }
                    }
                }
        }
    }
}

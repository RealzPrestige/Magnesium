package dev.zprestige.magnesium.event.eventbus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class Config(

    val parallelScope: CoroutineScope = CoroutineScope(Dispatchers.Default),

    val thirdPartyCompatibility: Boolean = true,

    val annotationRequired: Boolean = false
)

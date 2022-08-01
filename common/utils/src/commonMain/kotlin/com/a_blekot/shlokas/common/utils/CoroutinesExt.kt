package com.a_blekot.shlokas.common.utils

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.lifecycleCoroutineScope(coroutineContext: CoroutineContext = Dispatchers.Main): CoroutineScope {
    val scope = CoroutineScope(coroutineContext)
    lifecycle.doOnDestroy(scope::cancel)

    return scope
}

fun CoroutineScope.cancelSafely(msg: String) {
    try {
        if (isActive) {
            cancel(msg)
        }
    } catch (e: IllegalStateException) {

    }
}
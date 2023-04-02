package com.touktw.architecture.mvi

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

object ExceptionHandler {
    private val listeners =
        mutableListOf<(context: CoroutineContext, throwable: Throwable) -> Unit>()
    internal val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        listeners.forEach { listener ->
            listener(coroutineContext, throwable)
        }
    }

    fun addListener(listener: (context: CoroutineContext, throwable: Throwable) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (context: CoroutineContext, throwable: Throwable) -> Unit) {
        listeners.remove(listener)
    }
}
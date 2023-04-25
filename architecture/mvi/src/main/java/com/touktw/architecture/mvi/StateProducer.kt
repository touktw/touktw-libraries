package com.touktw.architecture.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

interface Action

interface State

interface SideEffect

internal interface StateProducer<STATE : State, ACTION : Action, SIDE_EFFECT : SideEffect> {
    val state: StateFlow<STATE>

    val sideEffect: Flow<SIDE_EFFECT>

    val dispatchAction: (ACTION) -> Unit

    val dispatchSideEffect: (SIDE_EFFECT) -> Unit

    val reduceState: (STATE.() -> STATE) -> Unit

    val withState: (STATE.() -> Unit) -> Unit
}

internal fun <STATE : State, ACTION : Action, SIDE_EFFECT : SideEffect> stateProducer(
    scope: CoroutineScope,
    initialState: STATE,
    actionProcessor: suspend (ACTION) -> Unit,
    dropActionDuringProcess: Boolean = true,
): StateProducer<STATE, ACTION, SIDE_EFFECT> =
    object : StateProducer<STATE, ACTION, SIDE_EFFECT> {
        private val actions = MutableSharedFlow<ACTION>()

        private val backingState = MutableStateFlow(initialState)
        override val state: StateFlow<STATE>
            get() = backingState

        private val backingSideEffect: Channel<SIDE_EFFECT> = Channel()
        override val sideEffect: Flow<SIDE_EFFECT>
            get() = backingSideEffect.receiveAsFlow()

        override val dispatchAction: (ACTION) -> Unit = { action ->
            scope.launch(ExceptionHandler.handler) { actions.emit(action) }
        }

        override val dispatchSideEffect: (SIDE_EFFECT) -> Unit = { sideEffect ->
            scope.launch(ExceptionHandler.handler) { backingSideEffect.send(sideEffect) }
        }

        override val reduceState: (STATE.() -> STATE) -> Unit = { reduce ->
            scope.launch(ExceptionHandler.handler) {
                backingState.value = backingState.value.reduce()
            }
        }

        override val withState: (STATE.() -> Unit) -> Unit = { with ->
            scope.launch(ExceptionHandler.handler) { backingState.value.with() }
        }

        init {
            actions.dropDuringCollecting(
                scope = scope,
                dropDuringCollecting = dropActionDuringProcess
            ) { action ->
                actionProcessor(action)
            }
        }
    }

private fun <T> Flow<T>.dropDuringCollecting(
    scope: CoroutineScope,
    dropDuringCollecting: Boolean = true,
    collector: FlowCollector<T>,
) = flow {
    val completed = AtomicBoolean(true)
    collect { value ->
        if (dropDuringCollecting && completed.compareAndSet(true, false)) {
            emit(value to completed)
        }
    }
}.conflate()
    .onEach {
        scope.launch(ExceptionHandler.handler) {
            val value = it.first
            val completed = it.second
            try {
                collector.emit(value)
            } catch (e: Exception) {
                throw e
            } finally {
                completed.set(true)
            }
        }
    }
    .launchIn(scope)
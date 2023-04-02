package com.touktw.architecture.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<STATE : State, ACTION : Action, SIDE_EFFECT : SideEffect>(
    initialState: STATE,
    dropActionDuringProcess: Boolean = true,
) : ViewModel() {
    private val stateProducer: StateProducer<STATE, ACTION, SIDE_EFFECT> by lazy {
        stateProducer(
            scope = viewModelScope,
            initialState = initialState,
            actionProcessor = { action -> onAction(action) },
            dropActionDuringProcess = dropActionDuringProcess
        )
    }

    val state: StateFlow<STATE>
        get() = stateProducer.state

    val sideEffect: Flow<Any>
        get() = stateProducer.sideEffect

    val dispatchAction: (ACTION) -> Unit
        get() = stateProducer.dispatchAction

    val dispatchSideEffect: (SIDE_EFFECT) -> Unit
        get() = stateProducer.dispatchSideEffect

    val reduceState: (STATE.() -> STATE) -> Unit
        get() = stateProducer.reduceState

    val withState: (STATE.() -> Unit) -> Unit
        get() = stateProducer.withState

    abstract suspend fun onAction(action: ACTION)
}
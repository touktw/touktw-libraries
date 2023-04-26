package com.touktw.architecture.mvi

class TestMviViewModel : BaseMviViewModel<TestState, TestActions, TestSideEffect>(
    TestState()
) {
    override suspend fun onAction(action: TestActions) {
        when (action) {
            TestActions.NormalAction -> {
                reduceState {
                    copy(result = "success")
                }
            }
            is TestActions.ExceptionAction -> {
                throw action.exception
            }
            TestActions.ResetAction -> {
                reduceState { copy(result = "") }
            }
            TestActions.SideEffectAction -> {
                dispatchSideEffect(TestSideEffect.NormalSideEffect)
            }
            TestActions.SideEffectAction2 -> {
                dispatchSideEffect(TestSideEffect.NormalSideEffect2)
            }
        }
    }
}

data class TestState(
    val result: String = "",
) : State

interface TestActions : Action {
    object ResetAction : TestActions
    object NormalAction : TestActions
    data class ExceptionAction(val exception: Exception) : TestActions
    object SideEffectAction : TestActions
    object SideEffectAction2 : TestActions
}

interface TestSideEffect : SideEffect {
    object NormalSideEffect : TestSideEffect
    object NormalSideEffect2 : TestSideEffect
}
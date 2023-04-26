package com.touktw.architecture.mvi

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

class StateProducerTest : BehaviorSpec(
    {
        val exception = Exception("test exception")
        var collectedException: Throwable? = null
        ExceptionHandler.addListener { context, throwable ->
            collectedException = throwable
        }

        val viewModel = TestMviViewModel()
        given("ViewModel 에서") {
            `when`("NormalAction 이 돌면") {
                viewModel.dispatchAction(TestActions.NormalAction)
                then("result 는 success 이어야 한다.") {
                    viewModel.state.first().result shouldBe "success"
                }
            }
            `when`("ResetAction 이 돌면") {
                viewModel.dispatchAction(TestActions.ResetAction)
                then("result 는 empty string 이어야 한다.") {
                    viewModel.state.first().result shouldBe ""
                }
            }
            `when`("ExceptionAction 이 터지면") {
                collectedException = null
                viewModel.dispatchAction(TestActions.ResetAction)
                viewModel.dispatchAction(TestActions.ExceptionAction(exception))
                then("Exception Handler 가 수집 되어야 한다.") {
                    collectedException shouldBe exception
                }
            }
            `when`("익셉션이 터진 후 정상적인 액션이 들어오면") {
                collectedException = null
                viewModel.dispatchAction(TestActions.ResetAction)
                viewModel.dispatchAction(TestActions.ExceptionAction(exception))
                viewModel.dispatchAction(TestActions.NormalAction)
                then("정상적으로 action 이 돌아야 한다.") {
                    viewModel.state.first().result shouldBe "success"
                }
                then("Exception Handler 가 수집 되어야 한다.") {
                    collectedException shouldBe exception
                }
            }
            `when`("사이드이펙트가 발생하면") {
                viewModel.dispatchAction(TestActions.SideEffectAction)
                then("사이드이펙트가 수집된다") {
                    viewModel.sideEffect.first().shouldBe(TestSideEffect.NormalSideEffect)
                }
                viewModel.dispatchAction(TestActions.SideEffectAction2)
                then("다른 이펙트가 발생하면 수집한다") {
                    viewModel.sideEffect.first().shouldBe(TestSideEffect.NormalSideEffect2)
                }
            }
        }
    }
)


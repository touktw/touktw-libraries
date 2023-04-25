package com.touktw.architecture.mvi

import io.kotest.core.config.AbstractProjectConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

object KotestConfig : AbstractProjectConfig() {
    override suspend fun beforeProject() {
        super.beforeProject()
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    override suspend fun afterProject() {
        super.afterProject()
        Dispatchers.resetMain()
    }
}
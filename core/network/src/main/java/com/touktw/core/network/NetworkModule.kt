package com.touktw.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttp(
        interceptors: Interceptors,
    ): OkHttpClient =
        OkHttpClient.Builder().run {
            build()
        }

    @Provides
    fun provideInterceptor(): Interceptors = emptyList()
}

typealias Interceptors = List<Interceptor>

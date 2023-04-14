package com.touktw.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * 네트워크에 필요한 최소한의 모듈만 제공 합니다.
 * 사용 하실땐 별도의 모듈을 구성 해서 사용하면 됩니다.
 *
 *    @Moudle
 *    @InstallIn(SingletonComponent::class)
 *    object SampleNetworkModule {
 *        @Provides
 *        @Singleton
 *        fun providesBaseUrl(): String = "https://www.example.com
 *
 *        @Provides
 *        fun providesSampleService(
 *            baseUrl: String,
 *            retrofit: Retrofit,
 *            okHttpClient: OkHttpClient,
 *        ): SampleService = retrofit.newBuilder().run {
 *            client(
 *                okHttpClient.newBuilder().run {
 *                    addInterceptor(...
 *                    build()
 *                }
 *            )
 *            addConverterFactory(...
 *            baseUrl(baseUrl)
 *            build()
 *        }.create(SampleService::class.java)
 *    }
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun providesOkHttp(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = createRetrofit(
        okHttpClient = okHttpClient
    )

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
    ) = Retrofit.Builder().run {
        baseUrl("http://localhost/")
        client(okHttpClient)
        build()
    }
}

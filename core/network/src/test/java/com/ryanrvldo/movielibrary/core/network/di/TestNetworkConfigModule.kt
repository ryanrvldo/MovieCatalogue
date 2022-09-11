/*
 * Copyright 2022 Rian Rivaldo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ryanrvldo.movielibrary.core.network.di

import com.ryanrvldo.movielibrary.core.network.config.AuthInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TestNetworkConfigModule {

    private fun providesAuthInterceptor(): Interceptor = AuthInterceptor()

    private fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(providesAuthInterceptor())
        .dispatcher(
            Dispatcher().apply {
                maxRequestsPerHost = 10
            }
        )
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    fun providesRetrofit(
        baseUrl: HttpUrl,
        client: OkHttpClient = providesOkHttpClient()
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

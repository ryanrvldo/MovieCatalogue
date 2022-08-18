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

package com.ryanrvldo.movielibrary.core.network.config

import com.ryanrvldo.movielibrary.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor : Interceptor {

    companion object {
        const val API_KEY_PARAM = "api_key"
    }

    override fun intercept(chain: Chain): Response {
        val prevRequest = chain.request()
        val newUrl = prevRequest.url.newBuilder()
            .addQueryParameter(API_KEY_PARAM, BuildConfig.TMDB_API_KEY)
            .build()
        val newRequest = prevRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}

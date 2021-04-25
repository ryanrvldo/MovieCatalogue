package com.ryanrvldo.core.data.source.remote.network.config

import com.ryanrvldo.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val prevRequest = chain.request()
        val newUrl = prevRequest.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val newRequest = prevRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}

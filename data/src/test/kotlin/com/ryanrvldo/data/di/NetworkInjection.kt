package com.ryanrvldo.data.di

import com.ryanrvldo.data.network.config.AuthInterceptor
import com.ryanrvldo.data.network.service.MovieService
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object NetworkInjection {

    private fun provideOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    fun provideRetrofit(baseUrl: HttpUrl): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create()

}
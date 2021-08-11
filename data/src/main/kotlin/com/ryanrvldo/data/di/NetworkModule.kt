package com.ryanrvldo.data.di

import com.ryanrvldo.data.BuildConfig
import com.ryanrvldo.data.network.config.AuthInterceptor
import com.ryanrvldo.data.network.service.MovieService
import com.ryanrvldo.data.network.service.TvShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClientBuilder(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create()

    @Provides
    @Singleton
    fun provideTvShowService(retrofit: Retrofit): TvShowService = retrofit.create()

}

package com.ryanrvldo.core.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ryanrvldo.core.BuildConfig
import com.ryanrvldo.core.data.source.remote.network.config.NetworkInterceptor
import com.ryanrvldo.core.data.source.remote.network.service.MovieService
import com.ryanrvldo.core.data.source.remote.network.service.TvShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClientBuilder(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(NetworkInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideTvShowService(retrofit: Retrofit): TvShowService {
        return retrofit.create(TvShowService::class.java)
    }

}

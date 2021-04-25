package com.ryanrvldo.moviecatalogue.di

import android.content.Context
import androidx.room.Room
import com.ryanrvldo.moviecatalogue.data.local.database.AppDatabase
import com.ryanrvldo.moviecatalogue.data.local.database.MovieDao
import com.ryanrvldo.moviecatalogue.data.local.database.SearchDao
import com.ryanrvldo.moviecatalogue.data.local.database.TvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_catalogue_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao = database.movieDao()

    @Singleton
    @Provides
    fun provideTvShowDao(database: AppDatabase): TvShowDao = database.tvShowDao()

    @Singleton
    @Provides
    fun provideSearchDao(database: AppDatabase): SearchDao = database.searchDao()
}

package com.ryanrvldo.data.di

import android.content.Context
import androidx.room.Room
import com.ryanrvldo.data.constants.Constants
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
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        com.ryanrvldo.data.database.AppDatabase::class.java,
        Constants.APP_DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideMovieDao(database: com.ryanrvldo.data.database.AppDatabase): com.ryanrvldo.data.database.dao.MovieFavoriteDao =
        database.movieFavoriteDao()

    @Singleton
    @Provides
    fun provideTvShowDao(database: com.ryanrvldo.data.database.AppDatabase): com.ryanrvldo.data.database.dao.TvShowFavoriteDao =
        database.tvShowFavoriteDao()

    @Singleton
    @Provides
    fun provideSearchDao(database: com.ryanrvldo.data.database.AppDatabase): com.ryanrvldo.data.database.dao.SearchHistoryDao =
        database.searchHistoryDao()

}

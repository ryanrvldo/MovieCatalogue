package com.ryanrvldo.core.di.modules

import android.content.Context
import androidx.room.Room
import com.ryanrvldo.core.data.source.local.database.AppDatabase
import com.ryanrvldo.core.data.source.local.database.dao.MovieFavoriteDao
import com.ryanrvldo.core.data.source.local.database.dao.SearchHistoryDao
import com.ryanrvldo.core.data.source.local.database.dao.TvShowFavoriteDao
import com.ryanrvldo.core.util.Constants
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
        AppDatabase::class.java,
        Constants.APP_DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase): MovieFavoriteDao = database.movieFavoriteDao()

    @Singleton
    @Provides
    fun provideTvShowDao(database: AppDatabase): TvShowFavoriteDao = database.tvShowFavoriteDao()

    @Singleton
    @Provides
    fun provideSearchDao(database: AppDatabase): SearchHistoryDao = database.searchHistoryDao()

}

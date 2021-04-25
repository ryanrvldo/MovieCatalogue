package com.ryanrvldo.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ryanrvldo.core.data.source.local.database.dao.MovieFavoriteDao
import com.ryanrvldo.core.data.source.local.database.dao.SearchHistoryDao
import com.ryanrvldo.core.data.source.local.database.dao.TvShowFavoriteDao
import com.ryanrvldo.core.data.source.local.entity.MovieFavoriteEntity
import com.ryanrvldo.core.data.source.local.entity.SearchHistoryEntity
import com.ryanrvldo.core.data.source.local.entity.TvShowFavoriteEntity

@Database(
    entities = [MovieFavoriteEntity::class, TvShowFavoriteEntity::class, SearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieFavoriteDao(): MovieFavoriteDao
    abstract fun tvShowFavoriteDao(): TvShowFavoriteDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}

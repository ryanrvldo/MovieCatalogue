package com.ryanrvldo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ryanrvldo.data.database.dao.MovieFavoriteDao
import com.ryanrvldo.data.database.dao.SearchHistoryDao
import com.ryanrvldo.data.database.dao.TvShowFavoriteDao
import com.ryanrvldo.data.database.entity.MovieFavoriteEntity
import com.ryanrvldo.data.database.entity.SearchHistoryEntity
import com.ryanrvldo.data.database.entity.TvShowFavoriteEntity

@Database(
    entities = [
        MovieFavoriteEntity::class,
        TvShowFavoriteEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieFavoriteDao(): MovieFavoriteDao
    abstract fun tvShowFavoriteDao(): TvShowFavoriteDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}

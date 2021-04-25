package com.ryanrvldo.moviecatalogue.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.model.Search
import com.ryanrvldo.moviecatalogue.data.model.TvShow

@Database(
    entities = [Movie::class, TvShow::class, Search::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun searchDao(): SearchDao
}
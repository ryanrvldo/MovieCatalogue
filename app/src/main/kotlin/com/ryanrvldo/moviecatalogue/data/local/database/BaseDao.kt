package com.ryanrvldo.moviecatalogue.data.local.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: T)

    @Delete
    suspend fun delete(t: T)

}
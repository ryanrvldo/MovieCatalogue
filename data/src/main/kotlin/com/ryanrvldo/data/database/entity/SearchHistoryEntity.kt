package com.ryanrvldo.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.*

@Entity(tableName = "search_histories")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val query: String,
    val date: Date
)

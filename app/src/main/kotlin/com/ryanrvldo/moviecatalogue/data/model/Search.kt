package com.ryanrvldo.moviecatalogue.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "query_search")
data class Search(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: String
) {
    constructor(query: String) : this(0, query)
}
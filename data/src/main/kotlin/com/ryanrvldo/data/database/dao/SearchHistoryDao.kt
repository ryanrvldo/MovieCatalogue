package com.ryanrvldo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ryanrvldo.data.database.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao : BaseDao<SearchHistoryEntity> {

    @Query("SELECT * FROM search_histories ORDER BY id DESC")
    fun getSearchQueries(): LiveData<List<SearchHistoryEntity>>

    @Query("SELECT `query` FROM search_histories WHERE `query`=:query")
    suspend fun selectSearchQuery(query: String): String

    @Query("DELETE FROM search_histories")
    suspend fun deleteSearchQueries()

}

package com.ryanrvldo.data.source.local

import com.ryanrvldo.data.database.dao.SearchHistoryDao
import com.ryanrvldo.data.database.entity.SearchHistoryEntity
import javax.inject.Inject

class SearchHistoryLocalDataSource @Inject constructor(private val searchDao: SearchHistoryDao) {

    fun getSearchHistories() = searchDao.getSearchQueries()

    suspend fun addSearchHistory(search: SearchHistoryEntity) = searchDao.insert(search)

    suspend fun removeSearchHistory(search: SearchHistoryEntity) = searchDao.delete(search)

    suspend fun removeSearchHistories() = searchDao.deleteSearchQueries()

    suspend fun getSearch(query: String): String = searchDao.selectSearchQuery(query)

}
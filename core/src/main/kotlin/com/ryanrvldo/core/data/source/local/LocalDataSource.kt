package com.ryanrvldo.core.data.source.local

import com.ryanrvldo.core.data.source.local.database.dao.MovieFavoriteDao
import com.ryanrvldo.core.data.source.local.database.dao.SearchHistoryDao
import com.ryanrvldo.core.data.source.local.database.dao.TvShowFavoriteDao
import com.ryanrvldo.core.data.source.local.entity.MovieFavoriteEntity
import com.ryanrvldo.core.data.source.local.entity.SearchHistoryEntity
import com.ryanrvldo.core.data.source.local.entity.TvShowFavoriteEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieFavoriteDao,
    private val tvShowDao: TvShowFavoriteDao,
    private val searchDao: SearchHistoryDao,
) {

    fun getFavoriteMovies() = movieDao.getFavoriteMovies()

    suspend fun addMovie(movie: MovieFavoriteEntity) = movieDao.insert(movie)

    suspend fun removeMovie(movie: MovieFavoriteEntity) = movieDao.delete(movie)

    suspend fun isMovieExists(id: Int): Boolean = movieDao.isMovieExists(id)

    fun getFavoriteTvShows() = tvShowDao.getFavoriteTvShows()

    suspend fun addTvShow(tvShow: TvShowFavoriteEntity) = tvShowDao.insert(tvShow)

    suspend fun removeTvShow(tvShow: TvShowFavoriteEntity) = tvShowDao.delete(tvShow)

    suspend fun isTvShowExists(id: Int): Boolean = tvShowDao.isTvShowExists(id)

    fun getSearchHistories() = searchDao.getSearchQueries()

    suspend fun addSearchQuery(search: SearchHistoryEntity) = searchDao.insert(search)

    suspend fun removeSearchQuery(search: SearchHistoryEntity) = searchDao.delete(search)

    suspend fun removeSearchHistories() = searchDao.deleteSearchQueries()

    suspend fun getSearch(query: String): String = searchDao.selectSearchQuery(query)

}

package com.ryanrvldo.data.source.local

import com.ryanrvldo.data.database.dao.TvShowFavoriteDao
import com.ryanrvldo.data.database.entity.TvShowFavoriteEntity
import javax.inject.Inject

class TvLocalDataSource @Inject constructor(private val tvShowFavoriteDao: TvShowFavoriteDao) {

    fun getFavoriteTvShows() = tvShowFavoriteDao.getFavoriteTvShows()

    suspend fun addFavoriteTv(tvShow: TvShowFavoriteEntity) = tvShowFavoriteDao.insert(tvShow)

    suspend fun removeFavoriteTv(tvShow: TvShowFavoriteEntity) = tvShowFavoriteDao.delete(tvShow)

    suspend fun isFavoriteTv(tvShowId: Int): Boolean = tvShowFavoriteDao.isTvShowExists(tvShowId)


}
package com.ryanrvldo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ryanrvldo.data.database.entity.TvShowFavoriteEntity

@Dao
interface TvShowFavoriteDao : BaseDao<TvShowFavoriteEntity> {

    @Query("SELECT * FROM tv_show_favorites")
    fun getFavoriteTvShows(): LiveData<List<TvShowFavoriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM tv_show_favorites WHERE id=:id)")
    suspend fun isTvShowExists(id: Int): Boolean

}

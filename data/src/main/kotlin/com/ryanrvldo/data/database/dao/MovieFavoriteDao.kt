package com.ryanrvldo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ryanrvldo.data.database.entity.MovieFavoriteEntity

@Dao
interface MovieFavoriteDao : BaseDao<MovieFavoriteEntity> {

    @Query("SELECT * FROM movie_favorites ORDER BY id DESC")
    fun getFavoriteMovies(): LiveData<List<MovieFavoriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM movie_favorites WHERE id=:id)")
    suspend fun isMovieExists(id: Int): Boolean

}

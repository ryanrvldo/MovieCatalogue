package com.ryanrvldo.moviecatalogue.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ryanrvldo.moviecatalogue.data.model.Movie

@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM favorite_movie ORDER BY id DESC")
    fun getFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_movie WHERE id=:id)")
    suspend fun isMovieExists(id: Int): Boolean

}
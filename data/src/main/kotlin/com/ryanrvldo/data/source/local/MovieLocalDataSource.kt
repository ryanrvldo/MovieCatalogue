package com.ryanrvldo.data.source.local

import com.ryanrvldo.data.database.dao.MovieFavoriteDao
import com.ryanrvldo.data.database.entity.MovieFavoriteEntity
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val movieFavoriteDao: MovieFavoriteDao) {

    fun getFavoriteMovies() = movieFavoriteDao.getFavoriteMovies()

    suspend fun addFavoriteMovie(movie: MovieFavoriteEntity) = movieFavoriteDao.insert(movie)

    suspend fun removeFavoriteMovie(movie: MovieFavoriteEntity) = movieFavoriteDao.delete(movie)

    suspend fun isFavoriteMovie(id: Int): Boolean = movieFavoriteDao.isMovieExists(id)

}
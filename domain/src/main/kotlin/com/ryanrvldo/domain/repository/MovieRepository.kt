package com.ryanrvldo.domain.repository

import androidx.paging.PagingData
import com.ryanrvldo.domain.model.movies.Movie
import com.ryanrvldo.domain.model.movies.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesByCategory(category: String): Flow<Result<PagingData<Movie>>>

    suspend fun getMovieDetails(id: Int): Result<MovieDetails>

}
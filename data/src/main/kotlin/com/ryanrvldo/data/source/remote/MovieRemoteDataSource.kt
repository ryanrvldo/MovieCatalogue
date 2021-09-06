package com.ryanrvldo.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingData
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.data.network.service.MovieService
import com.ryanrvldo.data.source.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService,
    private val moviePagingSource: MoviePagingSource
) {

    fun getMoviesByCategory(category: String): Flow<PagingData<MovieResponse>> {
        moviePagingSource.setCategory(category)
        val pager = Pager(moviePagingSource.pagingConfig) { moviePagingSource }
        return pager.flow
    }

    suspend fun getMovieDetails(id: Int): MovieResponse = service.getDetails(id)

}
package com.ryanrvldo.data.source.remote

import com.ryanrvldo.data.network.ApiResponse
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.data.network.service.MovieService
import com.ryanrvldo.data.util.handleNetworkException
import com.ryanrvldo.data.util.handleNetworkExceptionFromPagingResponse
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService,
    private val simpleDateFormat: SimpleDateFormat
) {

    suspend fun getMoviesByCategory(category: String): Flow<ApiResponse<List<MovieResponse>>> {
        return handleNetworkExceptionFromPagingResponse { service.getByCategory(category) }
    }

    suspend fun getNewReleaseMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        val currentDate: String = simpleDateFormat.format(Date())
        return handleNetworkExceptionFromPagingResponse {
            service.getNewReleases(currentDate, currentDate)
        }
    }

    suspend fun getMovieById(id: Int): Flow<ApiResponse<MovieResponse>> = handleNetworkException {
        service.getDetails(id)
    }

    suspend fun searchMovies(query: String): Flow<ApiResponse<List<MovieResponse>>> {
        return handleNetworkExceptionFromPagingResponse { service.search(query) }
    }

}
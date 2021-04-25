package com.ryanrvldo.core.data.source.remote

import com.ryanrvldo.core.data.source.remote.network.ApiResponse
import com.ryanrvldo.core.data.source.remote.network.service.MovieService
import com.ryanrvldo.core.data.source.remote.network.service.TvShowService
import com.ryanrvldo.core.data.source.remote.response.movies.MovieResponse
import com.ryanrvldo.core.data.source.remote.response.tvshows.SeasonResponse
import com.ryanrvldo.core.data.source.remote.response.tvshows.TvShowResponse
import com.ryanrvldo.core.util.ResponseHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieService: MovieService,
    private val tvShowService: TvShowService,
    private val simpleDateFormat: SimpleDateFormat,
) {

    suspend fun getMoviesByCategory(category: String): Flow<ApiResponse<List<MovieResponse>>> {
        return ResponseHelper.handlePagingResponse { movieService.getMoviesByCategory(category) }
    }

    suspend fun getNewReleaseMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        val currentDate: String = simpleDateFormat.format(Date())
        return ResponseHelper.handlePagingResponse {
            movieService.getNewReleaseMovies(currentDate, currentDate)
        }
    }

    suspend fun getMovieById(id: Int): Flow<ApiResponse<MovieResponse>> = flow {
        val response = movieService.getMovieById(id)
        val movieId = response.id ?: -1
        if (movieId > 0) {
            emit(ApiResponse.Success(response))
        } else {
            emit(ApiResponse.Empty)
        }
    }.catch { ex ->
        emit(ApiResponse.Error(ex.message!!))
    }

    suspend fun searchMovies(query: String): Flow<ApiResponse<List<MovieResponse>>> {
        return ResponseHelper.handlePagingResponse {
            movieService.searchMovies(query)
        }
    }

    suspend fun getTvShowsByCategory(category: String): Flow<ApiResponse<List<TvShowResponse>>> {
        return ResponseHelper.handlePagingResponse { tvShowService.getTvShowsByCategory(category) }
    }

    suspend fun getNewReleaseTvShows(): Flow<ApiResponse<List<TvShowResponse>>> {
        val currentDate: String = simpleDateFormat.format(Date())
        return ResponseHelper.handlePagingResponse {
            tvShowService.getNewReleaseTvShow(currentDate, currentDate)
        }
    }

    suspend fun getTvShowsById(id: Int): Flow<ApiResponse<TvShowResponse>> {
        return flow {
            val response = tvShowService.getTvShowById(id)
            val movieId = response.id ?: -1
            if (movieId > 0) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { ex ->
            emit(ApiResponse.Error(ex.message!!))
        }
    }

    suspend fun getTvShowSeason(tvId: Int, seasonNumber: Int): Flow<ApiResponse<SeasonResponse>> {
        return flow {
            val response = tvShowService.getSeasonTvDetail(tvId, seasonNumber)
            val seasonId = response.id ?: -1
            if (seasonId > 0) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { ex ->
            emit(ApiResponse.Error(ex.message!!))
        }
    }

    suspend fun searchTvShows(query: String): Flow<ApiResponse<List<TvShowResponse>>> {
        return ResponseHelper.handlePagingResponse {
            tvShowService.searchTvShows(query)
        }
    }

}

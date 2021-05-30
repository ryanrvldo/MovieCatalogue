package com.ryanrvldo.data.source.remote

import com.ryanrvldo.data.network.ApiResponse
import com.ryanrvldo.data.network.response.tvshows.SeasonResponse
import com.ryanrvldo.data.network.response.tvshows.TvShowResponse
import com.ryanrvldo.data.network.service.TvShowService
import com.ryanrvldo.data.util.handleNetworkException
import com.ryanrvldo.data.util.handleNetworkExceptionFromPagingResponse
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TvRemoteDataSource @Inject constructor(
    private val service: TvShowService,
    private val simpleDateFormat: SimpleDateFormat
) {

    suspend fun getByCategory(category: String): Flow<ApiResponse<List<TvShowResponse>>> {
        return handleNetworkExceptionFromPagingResponse { service.getByCategory(category) }
    }

    suspend fun getNewReleases(): Flow<ApiResponse<List<TvShowResponse>>> {
        val currentDate: String = simpleDateFormat.format(Date())
        return handleNetworkExceptionFromPagingResponse {
            service.getNewReleases(currentDate, currentDate)
        }
    }

    suspend fun getById(id: Int): Flow<ApiResponse<TvShowResponse>> =
        handleNetworkException { service.getDetails(id) }

    suspend fun getSeason(tvId: Int, seasonNumber: Int): Flow<ApiResponse<SeasonResponse>> {
        return handleNetworkException { service.getSeasonDetails(tvId, seasonNumber) }
    }

    suspend fun searchTvShows(query: String): Flow<ApiResponse<List<TvShowResponse>>> {
        return handleNetworkExceptionFromPagingResponse { service.search(query) }
    }

}
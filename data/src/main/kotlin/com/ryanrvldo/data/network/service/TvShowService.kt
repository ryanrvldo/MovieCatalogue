package com.ryanrvldo.data.network.service

import com.ryanrvldo.data.constants.Constants
import com.ryanrvldo.data.network.response.PagingResponse
import com.ryanrvldo.data.network.response.tvshows.SeasonResponse
import com.ryanrvldo.data.network.response.tvshows.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET("tv/{category}")
    suspend fun getByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
    ): PagingResponse<TvShowResponse>

    @GET("discover/tv")
    fun getNewReleases(
        @Query("first_air_date.gte") startDate: String,
        @Query("first_air_date.lte") endDate: String,
    ): PagingResponse<TvShowResponse>

    @GET("tv/{tv_id}")
    suspend fun getDetails(
        @Path("tv_id") id: Int,
        @Query("append_to_response") appendQuery: String = Constants.TV_APPEND_QUERY,
    ): TvShowResponse

    @GET("tv/{tv_id}/season/{season_number}")
    fun getSeasonDetails(
        @Path("tv_id") id: Int,
        @Path("season_number") number: Int,
    ): SeasonResponse

    @GET("search/tv")
    suspend fun search(@Query("query") query: String): PagingResponse<TvShowResponse>
}

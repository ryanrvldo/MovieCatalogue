package com.ryanrvldo.core.data.source.remote.network.service

import com.ryanrvldo.core.data.source.remote.response.commons.PagingResponse
import com.ryanrvldo.core.data.source.remote.response.tvshows.SeasonResponse
import com.ryanrvldo.core.data.source.remote.response.tvshows.TvShowResponse
import com.ryanrvldo.core.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET("tv/{category}")
    suspend fun getTvShowsByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
    ): PagingResponse<TvShowResponse>

    @GET("discover/tv")
    fun getNewReleaseTvShow(
        @Query("first_air_date.gte") startDate: String,
        @Query("first_air_date.lte") endDate: String,
    ): PagingResponse<TvShowResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShowById(
        @Path("tv_id") id: Int,
        @Query("append_to_response") appendQuery: String = Constants.TV_APPEND_QUERY,
    ): TvShowResponse

    @GET("tv/{tv_id}/season/{season_number}")
    fun getSeasonTvDetail(
        @Path("tv_id") id: Int,
        @Path("season_number") number: Int,
    ): SeasonResponse

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("query") query: String,
    ): PagingResponse<TvShowResponse>
}

package com.ryanrvldo.data.network.service

import com.ryanrvldo.data.network.response.PagingResponse
import com.ryanrvldo.data.network.response.movies.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    companion object {
        const val DETAIL_APPEND_QUERY = "images,videos,credits,similar"
    }

    @GET("movie/{category}")
    suspend fun getByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
    ): PagingResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") id: Int,
        @Query("append_to_response") appendQuery: String = DETAIL_APPEND_QUERY,
    ): MovieResponse

    @GET("discover/movie")
    fun getNewReleases(
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String,
    ): PagingResponse<MovieResponse>

    @GET("search/movie")
    suspend fun search(@Query("query") query: String): PagingResponse<MovieResponse>

}

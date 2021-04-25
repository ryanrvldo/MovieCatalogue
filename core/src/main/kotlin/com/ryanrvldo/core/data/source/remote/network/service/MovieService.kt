package com.ryanrvldo.core.data.source.remote.network.service

import com.ryanrvldo.core.data.source.remote.response.commons.PagingResponse
import com.ryanrvldo.core.data.source.remote.response.movies.MovieResponse
import com.ryanrvldo.core.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{category}")
    suspend fun getMoviesByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
    ): PagingResponse<MovieResponse>

    @GET("discover/movie")
    fun getNewReleaseMovies(
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String,
    ): PagingResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") id: Int,
        @Query("append_to_response") appendQuery: String = Constants.MOVIE_APPEND_QUERY,
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
    ): PagingResponse<MovieResponse>

}

package com.ryanrvldo.moviecatalogue.data.remote

import com.ryanrvldo.moviecatalogue.BuildConfig.TMDB_API_KEY
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.model.Season
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.data.remote.response.MovieResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.TvShowResponse
import com.ryanrvldo.moviecatalogue.utils.Constants.MOVIE_APPEND_QUERY
import com.ryanrvldo.moviecatalogue.utils.Constants.TV_APPEND_QUERY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("movie/{category}")
    suspend fun getMovieResponse(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("append_to_response") appendQuery: String = MOVIE_APPEND_QUERY,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<Movie>

    @GET("tv/{category}")
    suspend fun getTvShowResponse(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<TvShowResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") id: Int,
        @Query("append_to_response") appendQuery: String = TV_APPEND_QUERY,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<TvShow>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<MovieResponse>

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<TvShowResponse>

    @GET("discover/movie")
    fun getNewReleaseMovies(
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Call<MovieResponse>

    @GET("discover/tv")
    fun getNewReleaseTVShow(
        @Query("first_air_date.gte") startDate: String,
        @Query("first_air_date.lte") endDate: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Call<TvShowResponse>

    @GET("tv/{tv_id}/season/{season_number}")
    fun getSeasonTvDetail(
        @Path("tv_id") id: Int,
        @Path("season_number") number: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Call<Season>
}
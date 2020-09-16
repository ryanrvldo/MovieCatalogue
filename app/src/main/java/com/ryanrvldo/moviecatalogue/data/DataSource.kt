package com.ryanrvldo.moviecatalogue.data

import androidx.lifecycle.LiveData
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.model.Search
import com.ryanrvldo.moviecatalogue.data.model.Season
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.data.remote.response.MovieResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.TvShowResponse
import com.ryanrvldo.moviecatalogue.data.vo.Resource

interface DataSource {
    fun getMovies(category: String): LiveData<Resource<MovieResponse>>

    fun searchMovies(query: String): LiveData<Resource<MovieResponse>>

    fun getNewReleaseMovies(): LiveData<MovieResponse>

    fun getMovieDetails(movieId: Int): LiveData<Resource<Movie>>

    fun getTvShows(category: String): LiveData<Resource<TvShowResponse>>

    fun searchTvShows(query: String): LiveData<Resource<TvShowResponse>>

    fun getNewReleaseTvShows(): LiveData<TvShowResponse>

    fun getTvShowDetails(tvShowId: Int): LiveData<Resource<TvShow>>

    fun getSeasonDetails(tvId: Int, seasonNumber: Int): LiveData<Season>

    fun getFavoriteMovies(): LiveData<List<Movie>>

    suspend fun addFavoriteMovie(movie: Movie)

    suspend fun removeFavoriteMovie(movie: Movie)

    suspend fun isFavoriteMovie(movieId: Int): Boolean

    fun getFavoriteTvShows(): LiveData<List<TvShow>>

    suspend fun addFavoriteTvShow(tvShow: TvShow)

    suspend fun removeFavoriteTvShow(tvShow: TvShow)

    suspend fun isFavoriteTvShow(tvShowId: Int): Boolean

    fun getSearchHistories(): LiveData<List<Search>>

    suspend fun addSearchQuery(search: Search)

    suspend fun removeSearchQuery(search: Search)

    suspend fun removeSearchHistories()

    suspend fun selectSearchQuery(query: String): String?
}
package com.ryanrvldo.moviecatalogue.data.remote

import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.model.Season
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.data.remote.response.MovieResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.TvShowResponse
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val tmdbAPI: TmdbAPI
) : ResponseHelper() {

    suspend fun getMovies(category: String): Resource<MovieResponse> = getResponseResult {
        tmdbAPI.getMovieResponse(category)
    }

    suspend fun getMovieDetails(id: Int): Resource<Movie> = getResponseResult {
        tmdbAPI.getMovieDetails(id)
    }

    suspend fun searchMovies(query: String) = getResponseResult {
        tmdbAPI.searchMovies(query)
    }

    fun getNewReleaseMovies(callback: LoadDataCallback<MovieResponse>) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = date.format(Date())
        tmdbAPI.getNewReleaseMovies(currentDate, currentDate)
            .enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>,
                    response: Response<MovieResponse?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { callback.onDataReceived(it) }
                    } else {
                        callback.onDataNotAvailable()
                    }
                }

                override fun onFailure(
                    call: Call<MovieResponse?>,
                    t: Throwable
                ) {
                    callback.onDataNotAvailable()
                }
            })
    }

    suspend fun getTvShows(category: String): Resource<TvShowResponse> = getResponseResult {
        tmdbAPI.getTvShowResponse(category)
    }

    suspend fun getTvDetail(id: Int): Resource<TvShow> = getResponseResult {
        tmdbAPI.getTvShowDetails(id)
    }

    suspend fun searchTvShows(query: String): Resource<TvShowResponse> = getResponseResult {
        tmdbAPI.searchTvShows(query)
    }

    fun getNewReleaseTvShows(callback: LoadDataCallback<TvShowResponse>) {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = date.format(Date())
        tmdbAPI.getNewReleaseTVShow(currentDate, currentDate)
            .enqueue(object : Callback<TvShowResponse?> {
                override fun onResponse(
                    call: Call<TvShowResponse?>,
                    response: Response<TvShowResponse?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { callback.onDataReceived(it) }
                    } else {
                        callback.onDataNotAvailable()
                    }
                }

                override fun onFailure(
                    call: Call<TvShowResponse?>,
                    t: Throwable
                ) {
                    callback.onDataNotAvailable()
                }
            })
    }

    fun getSeasonDetail(
        tvId: Int, seasonNumber: Int, callback: LoadDataCallback<Season>
    ) {
        EspressoIdlingResource.increment()
        tmdbAPI.getSeasonTvDetail(tvId, seasonNumber)
            .enqueue(object : Callback<Season?> {
                override fun onResponse(
                    call: Call<Season?>,
                    response: Response<Season?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { callback.onDataReceived(it) }
                    } else {
                        callback.onDataNotAvailable()
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(
                    call: Call<Season?>,
                    t: Throwable
                ) {
                    callback.onDataNotAvailable()
                    EspressoIdlingResource.decrement()
                }
            })
    }

}


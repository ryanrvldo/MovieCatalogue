package com.dicoding.moviecataloguerv

import com.dicoding.moviecataloguerv.model.Movie
import com.dicoding.moviecataloguerv.model.MoviesResponse
import com.dicoding.moviecataloguerv.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//singleton in kotlin
object Repository {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(Api::class.java)
    }

    fun getPopularMovies(
            page: Int = 1,
            onSuccess: (movies: List<Movie>) -> Unit,
            onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
                .enqueue(object : Callback<MoviesResponse> {
                    override fun onResponse(
                            call: Call<MoviesResponse>,
                            response: Response<MoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }

    fun getTopRatedMovies(
            page: Int = 1,
            onSuccess: (movies: List<Movie>) -> Unit,
            onError: () -> Unit
    ) {
        api.getTopRatedMovies(page = page)
                .enqueue(object : Callback<MoviesResponse> {
                    override fun onResponse(
                            call: Call<MoviesResponse>,
                            response: Response<MoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }

    fun getUpcomingMovies(
            page: Int = 1,
            onSuccess: (movies: List<Movie>) -> Unit,
            onError: () -> Unit
    ) {
        api.getUpcomingMovies(page = page)
                .enqueue(object : Callback<MoviesResponse> {
                    override fun onResponse(
                            call: Call<MoviesResponse>,
                            response: Response<MoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movies)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }
}
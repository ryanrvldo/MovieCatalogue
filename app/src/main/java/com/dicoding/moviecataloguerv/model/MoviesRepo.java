package com.dicoding.moviecataloguerv.model;

import androidx.annotation.NonNull;

import com.dicoding.moviecataloguerv.network.Api;
import com.dicoding.moviecataloguerv.network.GenresResponse;
import com.dicoding.moviecataloguerv.network.MovieResponse;
import com.dicoding.moviecataloguerv.network.TrailerResponse;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.getMoviesCallback;
import com.dicoding.moviecataloguerv.network.onGetMovieCallback;
import com.dicoding.moviecataloguerv.network.onGetTrailersCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepo {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "79cfc0b909c3e2a8083827dc3a084234";

    private static MoviesRepo moviesRepo;

    private Api api;

    public MoviesRepo(Api api) {
        this.api = api;
    }

    public static MoviesRepo getInstance() {
        if (moviesRepo == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            moviesRepo = new MoviesRepo(retrofit.create(Api.class));
        }
        return moviesRepo;
    }

    public void getMovies(String language, final getMoviesCallback callback) {
        api.getDiscoverMovies(API_KEY, language, 2)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            MovieResponse movieResponse = response.body();
                            if (movieResponse != null && movieResponse.getMovieItems() != null) {
                                callback.onSuccess(movieResponse.getMovieItems());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getGenres(String language, final getGenresCallback callback) {
        api.getMovieGenres(API_KEY, language)
                .enqueue(new Callback<GenresResponse>() {
                             @Override
                             public void onResponse(@NonNull Call<GenresResponse> call, @NonNull Response<GenresResponse> response) {
                                 if (response.isSuccessful()) {
                                     GenresResponse genresResponse = response.body();
                                     if (genresResponse != null && genresResponse.getGenres() != null) {
                                         callback.onSuccess(genresResponse.getGenres());
                                     } else {
                                         callback.onError();
                                     }
                                 } else {
                                     callback.onError();
                                 }
                             }

                             @Override
                             public void onFailure(@NonNull Call<GenresResponse> call, @NonNull Throwable t) {
                                 callback.onError();
                             }
                         }
                );
    }

    public void getMovie(int movieId, String language, final onGetMovieCallback callback) {
        api.getMovie(movieId, API_KEY, language)
                .enqueue(new Callback<MovieItems>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieItems> call, @NonNull Response<MovieItems> response) {
                        if (response.isSuccessful()) {
                            MovieItems movieItems = response.body();
                            if (movieItems != null) {
                                callback.onSuccess(movieItems);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieItems> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTrailers(int movieId, final onGetTrailersCallback callback) {
        api.getMovieTrailers(movieId, API_KEY, "en-us")
                .enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                        if (response.isSuccessful()) {
                            TrailerResponse trailerResponse = response.body();
                            if (trailerResponse != null && trailerResponse.getTrailers() != null) {
                                callback.onSuccess(trailerResponse.getTrailers());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }
}
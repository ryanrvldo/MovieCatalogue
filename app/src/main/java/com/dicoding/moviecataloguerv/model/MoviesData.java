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

public class MoviesData {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";

    private static MoviesData data;

    private Api api;

    private MoviesData(Api api) {
        this.api = api;
    }

    public static MoviesData getInstance() {
        if (data == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            data = new MoviesData(retrofit.create(Api.class));
        }
        return data;
    }

    public void getMovies(final getMoviesCallback callback) {
        api.getDiscoverMovies("79cfc0b909c3e2a8083827dc3a084234", LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            MovieResponse movieResponse = response.body();
                            if (movieResponse != null && movieResponse.getMovies() != null) {
                                callback.onSuccess(movieResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getGenres(final getGenresCallback callback) {
        api.getGenres("79cfc0b909c3e2a8083827dc3a084234", LANGUAGE)
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
                             public void onFailure(Call<GenresResponse> call, Throwable t) {
                                 callback.onError();
                             }
                         }
                );
    }

    public void getMovie(int movieId, final onGetMovieCallback callback) {
        api.getMovie(movieId, "79cfc0b909c3e2a8083827dc3a084234", LANGUAGE)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTrailers(int movieId, final onGetTrailersCallback callback) {
        api.getTrailers(movieId, "79cfc0b909c3e2a8083827dc3a084234", LANGUAGE)
                .enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
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
                    public void onFailure(Call<TrailerResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}

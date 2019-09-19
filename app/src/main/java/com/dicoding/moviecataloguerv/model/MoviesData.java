package com.dicoding.moviecataloguerv.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
    private static final String API_KEY = "79cfc0b909c3e2a8083827dc3a084234";

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

    public MutableLiveData<MovieResponse> getMovies(String language) {
        final MutableLiveData<MovieResponse> moviesData = new MutableLiveData<>();
        api.getDiscoverMovies(API_KEY, language, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            moviesData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        moviesData.setValue(null);
                    }
                });
        return moviesData;
    }

    public void getMovies(String language, final getMoviesCallback callback) {
        api.getDiscoverMovies(API_KEY, language, 1)
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
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getGenres(String language, final getGenresCallback callback) {
        api.getGenres(API_KEY, language)
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

    public void getMovie(int movieId, String language, final onGetMovieCallback callback) {
        api.getMovie(movieId, API_KEY, language)
                .enqueue(new Callback<MovieItems>() {
                    @Override
                    public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
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
                    public void onFailure(Call<MovieItems> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTrailers(int movieId, String language, final onGetTrailersCallback callback) {
        api.getTrailers(movieId, API_KEY, language)
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
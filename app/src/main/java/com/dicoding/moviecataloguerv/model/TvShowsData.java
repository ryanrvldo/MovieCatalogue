package com.dicoding.moviecataloguerv.model;

import androidx.annotation.NonNull;

import com.dicoding.moviecataloguerv.network.Api;
import com.dicoding.moviecataloguerv.network.GenresResponse;
import com.dicoding.moviecataloguerv.network.TrailerResponse;
import com.dicoding.moviecataloguerv.network.TvShowResponse;
import com.dicoding.moviecataloguerv.network.getGenresCallback;
import com.dicoding.moviecataloguerv.network.getTvShowCallback;
import com.dicoding.moviecataloguerv.network.onGetTrailersCallback;
import com.dicoding.moviecataloguerv.network.onGetTvShowCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowsData {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "79cfc0b909c3e2a8083827dc3a084234";

    private static TvShowsData data;

    private Api api;

    private TvShowsData(Api api) {
        this.api = api;
    }

    public static TvShowsData getInstance() {
        if (data == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            data = new TvShowsData(retrofit.create(Api.class));
        }
        return data;
    }

    public void getTvShows(String language, final getTvShowCallback callback) {
        api.getDiscoverTvShows(API_KEY, language, 1)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            TvShowResponse tvShowResponse = response.body();
                            if (tvShowResponse != null && tvShowResponse.getTvShowItems() != null) {
                                callback.onSuccess(tvShowResponse.getTvShowItems());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShowResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }


    public void getGenres(String language, final getGenresCallback callback) {
        api.getGenres(API_KEY, language)
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
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
                });
    }

    public void getTvShow(int tvShowId, String language, final onGetTvShowCallback callback) {
        api.getTvShow(tvShowId, API_KEY, language)
                .enqueue(new Callback<TvShowItems>() {
                    @Override
                    public void onResponse(Call<TvShowItems> call, Response<TvShowItems> response) {
                        if (response.isSuccessful()) {
                            TvShowItems tvShowItems = response.body();
                            if (tvShowItems != null) {
                                callback.onSuccess(tvShowItems);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShowItems> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTrailers(int tvShowId, String language, final onGetTrailersCallback callback) {
        api.getTrailers(tvShowId, API_KEY, language)
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
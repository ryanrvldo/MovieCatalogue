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
    private static final String LANGUAGE = "en-US";

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

    public void getTvShows(final getTvShowCallback callback) {
        api.getDiscoverTvShows("79cfc0b909c3e2a8083827dc3a084234", LANGUAGE, 1)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            TvShowResponse tvShowResponse = response.body();
                            if (tvShowResponse != null && tvShowResponse.getTvShows() != null) {
                                callback.onSuccess(tvShowResponse.getTvShows());
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


    public void getGenres(final getGenresCallback callback) {
        api.getGenres("79cfc0b909c3e2a8083827dc3a084234", LANGUAGE)
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

    public void getTvShow(int tvShowId, final onGetTvShowCallback callback) {
        api.getTvShow(tvShowId, "79cfc0b909c3e2a8083827dc3a084234", LANGUAGE)
                .enqueue(new Callback<TvShow>() {
                    @Override
                    public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                        if (response.isSuccessful()) {
                            TvShow tvShow = response.body();
                            if (tvShow != null) {
                                callback.onSuccess(tvShow);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShow> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTrailers(int tvShowId, final onGetTrailersCallback callback) {
        api.getTrailers(tvShowId, "79cfc0b909c3e2a8083827dc3a084234", LANGUAGE)
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

package com.ryanrvldo.moviecatalogue.data.source.remote;

import androidx.annotation.NonNull;

import com.ryanrvldo.moviecatalogue.BuildConfig;
import com.ryanrvldo.moviecatalogue.data.source.model.Movie;
import com.ryanrvldo.moviecatalogue.data.source.model.Season;
import com.ryanrvldo.moviecatalogue.data.source.model.TvShow;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.MovieResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.TvShowResponse;
import com.ryanrvldo.moviecatalogue.utils.EspressoIdlingResource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {

    private static RemoteRepository mInstance;
    private TmdbAPI tmdbAPI;

    private RemoteRepository(TmdbAPI tmdbAPI) {
        this.tmdbAPI = tmdbAPI;
    }

    private static OkHttpClient httpClientBuilder() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static RemoteRepository getInstance() {
        if (mInstance == null) {
            Retrofit tmdbBuilder = new Retrofit.Builder()
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClientBuilder())
                    .build();
            mInstance = new RemoteRepository(tmdbBuilder.create(TmdbAPI.class));
        }
        return mInstance;
    }

    public void getMovies(String category, LoadDataCallback<MovieResponse> callback) {
        EspressoIdlingResource.increment();
        tmdbAPI.getMovies(category, BuildConfig.TMDB_API_KEY, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call,
                                           @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onDataReceived(response.body());
                        } else {
                            callback.onDataNotAvailable();
                        }
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                        EspressoIdlingResource.decrement();
                    }
                });
    }

    public void searchMovies(String query, LoadDataCallback<MovieResponse> callback) {
        EspressoIdlingResource.increment();
        tmdbAPI.searchMovies(BuildConfig.TMDB_API_KEY, query)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call,
                                           @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onDataReceived(response.body());
                        } else {
                            callback.onDataNotAvailable();
                        }
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                        EspressoIdlingResource.decrement();
                    }
                });
    }

    public void getNewReleaseMovies(LoadDataCallback<MovieResponse> callback) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = date.format(new Date());
        tmdbAPI.getNewReleaseMovies(BuildConfig.TMDB_API_KEY, currentDate, currentDate)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call,
                                           @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getMovieItems() != null) {
                                callback.onDataReceived(response.body());
                            } else {
                                callback.onDataNotAvailable();
                            }
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    public void getMovieDetail(int id, LoadDataCallback<Movie> callback) {
        EspressoIdlingResource.increment();
        tmdbAPI.
                getMovie(id, BuildConfig.TMDB_API_KEY, "images,videos,credits,similar")
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NonNull Call<Movie> call,
                                           @NonNull Response<Movie> response) {
                        if (response.isSuccessful()) {
                            callback.onDataReceived(response.body());
                        } else {
                            callback.onDataNotAvailable();
                        }
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                        EspressoIdlingResource.decrement();
                    }
                });
    }

    public void getTvShows(String category, LoadDataCallback<TvShowResponse> callback) {
        EspressoIdlingResource.increment();
        tmdbAPI.getTvShows(category, BuildConfig.TMDB_API_KEY, 1)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call,
                                           @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onDataReceived(response.body());
                        } else {
                            callback.onDataNotAvailable();
                        }
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                        EspressoIdlingResource.decrement();
                    }
                });
    }

    public void searchTvShows(String query, LoadDataCallback<TvShowResponse> callback) {
        EspressoIdlingResource.increment();
        tmdbAPI.searchTvShows(BuildConfig.TMDB_API_KEY, query)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call,
                                           @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onDataReceived(response.body());
                        } else {
                            callback.onDataNotAvailable();
                        }
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                        EspressoIdlingResource.decrement();
                    }
                });
    }


    public void getNewReleaseTvShows(LoadDataCallback<TvShowResponse> callback) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = date.format(new Date());
        tmdbAPI.getNewReleaseTVShow(BuildConfig.TMDB_API_KEY, currentDate, currentDate)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call,
                                           @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getTvShowItems() != null) {
                                callback.onDataReceived(response.body());
                            } else {
                                callback.onDataNotAvailable();
                            }
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    public void getTvDetail(int id, LoadDataCallback<TvShow> callback) {
        EspressoIdlingResource.increment();
        tmdbAPI.getTvShow(id, BuildConfig.TMDB_API_KEY, "images,content_ratings,videos,credits,similar")
                .enqueue(new Callback<TvShow>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShow> call,
                                           @NonNull Response<TvShow> response) {
                        if (response.isSuccessful()) {
                            callback.onDataReceived(response.body());
                        } else {
                            callback.onDataNotAvailable();
                        }
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                        EspressoIdlingResource.decrement();
                    }
                });
    }

    public void getSeasonDetail(int tvId, int seasonNumber, LoadDataCallback<Season> callback) {
        EspressoIdlingResource.increment();
        tmdbAPI.getSeasonTvDetail(tvId, seasonNumber, BuildConfig.TMDB_API_KEY)
                .enqueue(new Callback<Season>() {
                    @Override
                    public void onResponse(@NonNull Call<Season> call,
                                           @NonNull Response<Season> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getEpisodes() != null) {
                                callback.onDataReceived(response.body());
                            } else {
                                callback.onDataNotAvailable();
                            }
                        } else {
                            callback.onDataNotAvailable();
                        }
                        EspressoIdlingResource.decrement();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Season> call, @NonNull Throwable t) {
                        callback.onDataNotAvailable();
                        EspressoIdlingResource.decrement();
                    }
                });
    }
}
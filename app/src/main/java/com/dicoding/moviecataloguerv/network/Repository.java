package com.dicoding.moviecataloguerv.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static Repository repository;

    private Api api;

    private Repository(Api api) {
        this.api = api;
    }

    private static OkHttpClient providesOkHttpClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();
    }

    public static Repository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            repository = new Repository(retrofit.create(Api.class));
        }
        return repository;
    }

    public MutableLiveData<MovieResponse> getMovies(String category, String language) {
        final MutableLiveData<MovieResponse> moviesData = new MutableLiveData<>();
        api.getMovies(category, BuildConfig.TMDB_API_KEY, language, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    moviesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                moviesData.setValue(null);
            }
        });
        return moviesData;
    }

    public MutableLiveData<GenresResponse> getGenres(String type, String language) {
        final MutableLiveData<GenresResponse> genresData = new MutableLiveData<>();
        api.getGenres(type, BuildConfig.TMDB_API_KEY, language).enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenresResponse> call, @NonNull Response<GenresResponse> response) {
                if (response.isSuccessful()) {
                    genresData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenresResponse> call, @NonNull Throwable t) {
                genresData.setValue(null);
            }
        });
        return genresData;
    }

    public MutableLiveData<MovieItems> getMovieItems(int movieId, String language) {
        final MutableLiveData<MovieItems> movieData = new MutableLiveData<>();
        api.getMovie(movieId, BuildConfig.TMDB_API_KEY, language).enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(@NonNull Call<MovieItems> call, @NonNull Response<MovieItems> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieItems> call, @NonNull Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }

    public MutableLiveData<TrailerResponse> getTrailers(String type, int movieId) {
        final MutableLiveData<TrailerResponse> trailersData = new MutableLiveData<>();
        api.getTrailers(type, movieId, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    trailersData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                trailersData.setValue(null);
            }
        });
        return trailersData;
    }

    //TvShow
    public MutableLiveData<TvShowResponse> getTvShows(String category, String language) {
        final MutableLiveData<TvShowResponse> tvShowsData = new MutableLiveData<>();
        api.getTvShows(category, BuildConfig.TMDB_API_KEY, language, 1).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    tvShowsData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                tvShowsData.setValue(null);
            }
        });
        return tvShowsData;
    }

    public MutableLiveData<TvShowItems> getTvShowItems(int tvShowId, String language) {
        final MutableLiveData<TvShowItems> tvShowData = new MutableLiveData<>();
        api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, language).enqueue(new Callback<TvShowItems>() {
            @Override
            public void onResponse(@NonNull Call<TvShowItems> call, @NonNull Response<TvShowItems> response) {
                if (response.isSuccessful()) {
                    tvShowData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowItems> call, @NonNull Throwable t) {
                tvShowData.setValue(null);
            }
        });
        return tvShowData;
    }

    public MutableLiveData<SimilarResponse> getSimilar(String type, int movieId) {
        final MutableLiveData<SimilarResponse> similarData = new MutableLiveData<>();
        api.getSimilar(type, movieId, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<SimilarResponse>() {
            @Override
            public void onResponse(@NonNull Call<SimilarResponse> call, @NonNull Response<SimilarResponse> response) {
                if (response.isSuccessful()) {
                    similarData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SimilarResponse> call, @NonNull Throwable t) {
                similarData.setValue(null);
            }
        });
        return similarData;
    }
}
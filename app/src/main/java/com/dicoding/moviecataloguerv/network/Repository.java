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

    public static Repository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new Repository(retrofit.create(Api.class));
        }
        return repository;
    }

    public MutableLiveData<MovieResponse> getMovies(String language) {
        final MutableLiveData<MovieResponse> moviesData = new MutableLiveData<>();
        api.getDiscoverMovies(BuildConfig.TMDB_API_KEY, language, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    moviesData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                moviesData.postValue(null);
            }
        });
        return moviesData;
    }

    public MutableLiveData<GenresResponse> getMovieGenres(String language) {
        final MutableLiveData<GenresResponse> genresData = new MutableLiveData<>();
        api.getMovieGenres(BuildConfig.TMDB_API_KEY, language).enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenresResponse> call, @NonNull Response<GenresResponse> response) {
                if (response.isSuccessful()) {
                    genresData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenresResponse> call, @NonNull Throwable t) {
                genresData.postValue(null);
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
                    movieData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieItems> call, @NonNull Throwable t) {
                movieData.postValue(null);
            }
        });
        return movieData;
    }

    public MutableLiveData<TrailerResponse> getMovieTrailers(int movieId) {
        final MutableLiveData<TrailerResponse> trailersData = new MutableLiveData<>();
        api.getMovieTrailers(movieId, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    trailersData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                trailersData.postValue(null);
            }
        });
        return trailersData;
    }

    //TvShow
    public MutableLiveData<TvShowResponse> getTvShows(String language) {
        final MutableLiveData<TvShowResponse> tvShowsData = new MutableLiveData<>();
        api.getDiscoverTvShows(BuildConfig.TMDB_API_KEY, language, 1).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    tvShowsData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                tvShowsData.postValue(null);
            }
        });
        return tvShowsData;
    }


    public MutableLiveData<GenresResponse> getTvGenres(String language) {
        final MutableLiveData<GenresResponse> genresData = new MutableLiveData<>();
        api.getTvGenres(BuildConfig.TMDB_API_KEY, language).enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenresResponse> call, @NonNull Response<GenresResponse> response) {
                if (response.isSuccessful()) {
                    genresData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GenresResponse> call, @NonNull Throwable t) {
                genresData.postValue(null);
            }
        });
        return genresData;
    }

    public MutableLiveData<TvShowItems> getTvShowItems(int tvShowId, String language) {
        final MutableLiveData<TvShowItems> tvShowData = new MutableLiveData<>();
        api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, language).enqueue(new Callback<TvShowItems>() {
            @Override
            public void onResponse(@NonNull Call<TvShowItems> call, @NonNull Response<TvShowItems> response) {
                if (response.isSuccessful()) {
                    tvShowData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowItems> call, @NonNull Throwable t) {
                tvShowData.postValue(null);
            }
        });
        return tvShowData;
    }

    public MutableLiveData<TrailerResponse> getTvTrailers(int tvShowId) {
        final MutableLiveData<TrailerResponse> trailersData = new MutableLiveData<>();
        api.getTvTrailers(tvShowId, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    trailersData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call, @NonNull Throwable t) {
                trailersData.postValue(null);
            }
        });
        return trailersData;
    }

    public MutableLiveData<SimilarResponse> getMovieSimilar(int movieId) {
        final MutableLiveData<SimilarResponse> similarData = new MutableLiveData<>();
        api.getMovieSimilar(movieId, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<SimilarResponse>() {
            @Override
            public void onResponse(Call<SimilarResponse> call, Response<SimilarResponse> response) {
                if (response.isSuccessful()) {
                    similarData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SimilarResponse> call, Throwable t) {
                similarData.postValue(null);
            }
        });
        return similarData;
    }

    public MutableLiveData<SimilarResponse> getTvSimilar(int tvShowId) {
        final MutableLiveData<SimilarResponse> similarData = new MutableLiveData<>();
        api.getTvSimilar(tvShowId, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<SimilarResponse>() {
            @Override
            public void onResponse(Call<SimilarResponse> call, Response<SimilarResponse> response) {
                if (response.isSuccessful()) {
                    similarData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SimilarResponse> call, Throwable t) {
                similarData.postValue(null);
            }
        });
        return similarData;
    }
}
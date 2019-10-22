package com.dicoding.moviecataloguerv.network;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.moviecataloguerv.BuildConfig;
import com.dicoding.moviecataloguerv.database.FavoriteDatabase;
import com.dicoding.moviecataloguerv.database.MovieDao;
import com.dicoding.moviecataloguerv.database.TvShowDao;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowResponse;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
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

    private LiveData<List<MovieItems>> allFavoriteMovies;
    private LiveData<List<TvShowItems>> allFavoriteTv;

    private MovieDao movieDao;
    private TvShowDao tvShowDao;


    private Repository(Api api) {
        this.api = api;
    }

    public Repository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        movieDao = database.movieDao();
        tvShowDao = database.tvShowDao();
        allFavoriteMovies = movieDao.getAllFavoriteMovie();
        allFavoriteTv = tvShowDao.getAllFavoriteTv();
    }

    private static OkHttpClient providesOkHttpClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(5, TimeUnit.SECONDS).retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS).build();
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

    public MutableLiveData<GenresResponse> getGenres(String type, String language) {
        final MutableLiveData<GenresResponse> genresData = new MutableLiveData<>();
        api.getGenres(type, BuildConfig.TMDB_API_KEY, language).enqueue(new Callback<GenresResponse>() {
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

    public MutableLiveData<TrailerResponse> getTrailers(String type, int movieId) {
        final MutableLiveData<TrailerResponse> trailersData = new MutableLiveData<>();
        api.getTrailers(type, movieId, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<TrailerResponse>() {
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
    public MutableLiveData<TvShowResponse> getTvShows(String category, String language) {
        final MutableLiveData<TvShowResponse> tvShowsData = new MutableLiveData<>();
        api.getTvShows(category, BuildConfig.TMDB_API_KEY, language, 1).enqueue(new Callback<TvShowResponse>() {
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

    public MutableLiveData<SimilarResponse> getSimilar(String type, int movieId, String category) {
        final MutableLiveData<SimilarResponse> similarData = new MutableLiveData<>();
        api.getSimilar(type, movieId, category, BuildConfig.TMDB_API_KEY, "en-us").enqueue(new Callback<SimilarResponse>() {
            @Override
            public void onResponse(@NonNull Call<SimilarResponse> call, @NonNull Response<SimilarResponse> response) {
                if (response.isSuccessful()) {
                    similarData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SimilarResponse> call, @NonNull Throwable t) {
                similarData.postValue(null);
            }
        });
        return similarData;
    }

    public MutableLiveData<MovieResponse> searchMovies(String query) {
        final MutableLiveData<MovieResponse> searchData = new MutableLiveData<>();
        api.searchMovies(BuildConfig.TMDB_API_KEY, "en-us", query).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    searchData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d("ERROR", Objects.requireNonNull(t.getMessage()));
            }
        });
        return searchData;
    }

    public MutableLiveData<TvShowResponse> searchTvShows(String query) {
        final MutableLiveData<TvShowResponse> searchData = new MutableLiveData<>();
        api.searchTvShows(BuildConfig.TMDB_API_KEY, "en-us", query).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    searchData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                Log.d("ERROR", Objects.requireNonNull(t.getMessage()));
            }
        });
        return searchData;
    }


    /*
     *
     * Database
     *
     */
    public void addFavMovie(MovieItems movieItems) {
        new AddFavMovieAsyncTask(movieDao).execute(movieItems);
    }

    public void deleteFavMovie(MovieItems movieItems) {
        new DeleteFavMovieAsyncTask(movieDao).execute(movieItems);
    }


    public MovieItems selectFavMovie(final int movieId) {
        MovieItems items = null;
        try {
            items = new SelectFavMovieAsyncTask(movieDao).execute(movieId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return items;
    }

    public LiveData<List<MovieItems>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void addFavTv(TvShowItems tvShowItems) {
        new AddFavTvAsyncTask(tvShowDao).execute(tvShowItems);
    }

    public void deleteFavTv(TvShowItems tvShowItems) {
        new DeleteFavTvAsyncTask(tvShowDao).execute(tvShowItems);
    }

    public TvShowItems selectFavTv(final int tvShowId) {
        TvShowItems items = null;
        try {
            items = new SelectFavTvAsyncTask(tvShowDao).execute(tvShowId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return items;
    }

    public LiveData<List<TvShowItems>> getAllFavoriteTv() {
        return allFavoriteTv;
    }


    private static class AddFavMovieAsyncTask extends AsyncTask<MovieItems, Void, Void> {

        private MovieDao movieDao;

        private AddFavMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieItems... movieItems) {
            movieDao.insert(movieItems[0]);
            return null;
        }

    }

    private static class DeleteFavMovieAsyncTask extends AsyncTask<MovieItems, Void, Void> {

        private MovieDao movieDao;

        private DeleteFavMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieItems... movieItems) {
            movieDao.delete(movieItems[0]);
            return null;
        }

    }

    private static class SelectFavMovieAsyncTask extends AsyncTask<Integer, Void, MovieItems> {

        private MovieDao movieDao;

        private SelectFavMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected MovieItems doInBackground(Integer... integers) {
            return movieDao.selectById(integers[0]);
        }
    }

    private static class AddFavTvAsyncTask extends AsyncTask<TvShowItems, Void, Void> {
        private TvShowDao tvShowDao;

        private AddFavTvAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Void doInBackground(TvShowItems... tvShowItems) {
            tvShowDao.insert(tvShowItems[0]);
            return null;
        }
    }

    private static class DeleteFavTvAsyncTask extends AsyncTask<TvShowItems, Void, Void> {
        private TvShowDao tvShowDao;

        private DeleteFavTvAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Void doInBackground(TvShowItems... tvShowItems) {
            tvShowDao.delete(tvShowItems[0]);
            return null;
        }
    }

    private static class SelectFavTvAsyncTask extends AsyncTask<Integer, Void, TvShowItems> {
        private TvShowDao tvShowDao;

        private SelectFavTvAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected TvShowItems doInBackground(Integer... integers) {
            return tvShowDao.selectById(integers[0]);
        }
    }

}


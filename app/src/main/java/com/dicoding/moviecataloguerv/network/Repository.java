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
import com.dicoding.moviecataloguerv.database.SearchDao;
import com.dicoding.moviecataloguerv.database.SearchDatabase;
import com.dicoding.moviecataloguerv.database.TvShowDao;
import com.dicoding.moviecataloguerv.model.CreditsResponse;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.Movie;
import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.Search;
import com.dicoding.moviecataloguerv.model.Season;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShow;
import com.dicoding.moviecataloguerv.model.TvShowResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    private LiveData<List<Movie>> allFavoriteMovies;
    private LiveData<List<TvShow>> allFavoriteTv;

    private LiveData<List<Search>> allSearch;

    private MovieDao movieDao;
    private TvShowDao tvShowDao;
    private SearchDao searchDao;


    private Repository(Api api) {
        this.api = api;
    }

    public Repository(Application application) {
        FavoriteDatabase database = FavoriteDatabase.getInstance(application);
        movieDao = database.movieDao();
        tvShowDao = database.tvShowDao();
        allFavoriteMovies = movieDao.getAllFavoriteMovie();
        allFavoriteTv = tvShowDao.getAllFavoriteTv();

        SearchDatabase searchDatabase = SearchDatabase.getInstance(application);
        searchDao = searchDatabase.searchDao();
        allSearch = searchDao.getAllSearch();
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

    public MutableLiveData<MovieResponse> getMovies(String category) {
        final MutableLiveData<MovieResponse> moviesData = new MutableLiveData<>();
        api.getMovies(category, BuildConfig.TMDB_API_KEY, 1).enqueue(new Callback<MovieResponse>() {
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

    public MutableLiveData<GenresResponse> getGenres(String type) {
        final MutableLiveData<GenresResponse> genresData = new MutableLiveData<>();
        api.getGenres(type, BuildConfig.TMDB_API_KEY).enqueue(new Callback<GenresResponse>() {
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

    public MutableLiveData<Movie> getMovieItems(int movieId) {
        final MutableLiveData<Movie> movieData = new MutableLiveData<>();
        api.getMovie(movieId, BuildConfig.TMDB_API_KEY, "images").enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    movieData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                movieData.postValue(null);
            }
        });
        return movieData;
    }

    public MutableLiveData<TrailerResponse> getTrailers(String type, int movieId) {
        final MutableLiveData<TrailerResponse> trailersData = new MutableLiveData<>();
        api.getTrailers(type, movieId, BuildConfig.TMDB_API_KEY).enqueue(new Callback<TrailerResponse>() {
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
    public MutableLiveData<TvShowResponse> getTvShows(String category) {
        final MutableLiveData<TvShowResponse> tvShowsData = new MutableLiveData<>();
        api.getTvShows(category, BuildConfig.TMDB_API_KEY, 1).enqueue(new Callback<TvShowResponse>() {
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

    public MutableLiveData<TvShow> getTvShowItems(int tvShowId) {
        final MutableLiveData<TvShow> tvShowData = new MutableLiveData<>();
        api.getTvShow(tvShowId, BuildConfig.TMDB_API_KEY, "images").enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                if (response.isSuccessful()) {
                    tvShowData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
                tvShowData.postValue(null);
            }
        });
        return tvShowData;
    }

    public MutableLiveData<SimilarResponse> getSimilar(String type, int movieId, String category) {
        final MutableLiveData<SimilarResponse> similarData = new MutableLiveData<>();
        api.getSimilar(type, movieId, category, BuildConfig.TMDB_API_KEY).enqueue(new Callback<SimilarResponse>() {
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

    public MutableLiveData<CreditsResponse> getCredits(String type, int movieId) {
        final MutableLiveData<CreditsResponse> creditsData = new MutableLiveData<>();
        api.getCredits(type, movieId, BuildConfig.TMDB_API_KEY).enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreditsResponse> call, @NonNull Response<CreditsResponse> response) {
                if (response.isSuccessful()) {
                    creditsData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreditsResponse> call, @NonNull Throwable t) {
                Log.e("REPOSITORY", "Error");
            }
        });
        return creditsData;
    }

    public LiveData<MovieResponse> searchMovies(String query) {
        final MutableLiveData<MovieResponse> searchData = new MutableLiveData<>();
        api.searchMovies(BuildConfig.TMDB_API_KEY, query).enqueue(new Callback<MovieResponse>() {
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

    public LiveData<TvShowResponse> searchTvShows(String query) {
        final MutableLiveData<TvShowResponse> searchData = new MutableLiveData<>();
        api.searchTvShows(BuildConfig.TMDB_API_KEY, query).enqueue(new Callback<TvShowResponse>() {
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

    public MutableLiveData<MovieResponse> newReleaseMovies() {
        final MutableLiveData<MovieResponse> newReleaseData = new MutableLiveData<>();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = date.format(new Date());
        api.getNewReleaseMovies(BuildConfig.TMDB_API_KEY, currentDate, currentDate).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMovieItems() != null) {
                        newReleaseData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {

            }
        });
        return newReleaseData;
    }

    public MutableLiveData<TvShowResponse> newReleaseTVShow() {
        final MutableLiveData<TvShowResponse> newReleaseData = new MutableLiveData<>();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = date.format(new Date());
        api.getNewReleaseTVShow(BuildConfig.TMDB_API_KEY, currentDate, currentDate).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getTvShowItems() != null) {
                        newReleaseData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {

            }
        });
        return newReleaseData;
    }

    public MutableLiveData<Season> getSeasons(int tvId, int seasonNumber) {
        final MutableLiveData<Season> seasonData = new MutableLiveData<>();
        api.getTvSeasons(tvId, seasonNumber, BuildConfig.TMDB_API_KEY).enqueue(new Callback<Season>() {
            @Override
            public void onResponse(@NonNull Call<Season> call, @NonNull Response<Season> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getEpisodes() != null) {
                        seasonData.postValue(response.body());
                        Log.d("Season:", response.body().toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Season> call, @NonNull Throwable t) {
            }
        });
        return seasonData;
    }


    /*
     *
     * Database
     *
     */
    public void addFavMovie(Movie movie) {
        new AddFavMovieAsyncTask(movieDao).execute(movie);
    }

    public void deleteFavMovie(Movie movie) {
        new DeleteFavMovieAsyncTask(movieDao).execute(movie);
    }


    public Movie selectFavMovie(final int movieId) {
        Movie items = null;
        try {
            items = new SelectFavMovieAsyncTask(movieDao).execute(movieId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return items;
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return allFavoriteMovies;
    }

    public void addFavTv(TvShow tvShow) {
        new AddFavTvAsyncTask(tvShowDao).execute(tvShow);
    }

    public void deleteFavTv(TvShow tvShow) {
        new DeleteFavTvAsyncTask(tvShowDao).execute(tvShow);
    }

    public TvShow selectFavTv(final int tvShowId) {
        TvShow items = null;
        try {
            items = new SelectFavTvAsyncTask(tvShowDao).execute(tvShowId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return items;
    }

    public LiveData<List<TvShow>> getAllFavoriteTv() {
        return allFavoriteTv;
    }

    public void addSearch(Search search) {
        new AddSearchAsyncTask(searchDao).execute(search);
    }

    public void deleteSearch(Search search) {
        new DeleteSearchAsyncTask(searchDao).execute(search);
    }

    public LiveData<List<Search>> getAllSearch() {
        return allSearch;
    }

    public void deleteAllSearch() {
        new DeleteAllSearchAsyncTask(searchDao).execute();
    }

    public String getSearch(final String query) {
        String search = null;
        try {
            search = new SelectSearchAsyncTask(searchDao).execute(query).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return search;
    }

    private static class AddFavMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        private AddFavMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movieItems) {
            movieDao.insert(movieItems[0]);
            return null;
        }
    }

    private static class DeleteFavMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        private DeleteFavMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movieItems) {
            movieDao.delete(movieItems[0]);
            return null;
        }

    }

    private static class SelectFavMovieAsyncTask extends AsyncTask<Integer, Void, Movie> {

        private MovieDao movieDao;

        private SelectFavMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Movie doInBackground(Integer... integers) {
            return movieDao.selectById(integers[0]);
        }
    }

    private static class AddFavTvAsyncTask extends AsyncTask<TvShow, Void, Void> {
        private TvShowDao tvShowDao;

        private AddFavTvAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShowItems) {
            tvShowDao.insert(tvShowItems[0]);
            return null;
        }
    }

    private static class DeleteFavTvAsyncTask extends AsyncTask<TvShow, Void, Void> {
        private TvShowDao tvShowDao;

        private DeleteFavTvAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShowItems) {
            tvShowDao.delete(tvShowItems[0]);
            return null;
        }
    }

    private static class SelectFavTvAsyncTask extends AsyncTask<Integer, Void, TvShow> {
        private TvShowDao tvShowDao;

        private SelectFavTvAsyncTask(TvShowDao tvShowDao) {
            this.tvShowDao = tvShowDao;
        }

        @Override
        protected TvShow doInBackground(Integer... integers) {
            return tvShowDao.selectById(integers[0]);
        }
    }

    private static class AddSearchAsyncTask extends AsyncTask<Search, Void, Void> {
        private SearchDao searchDao;

        private AddSearchAsyncTask(SearchDao searchDao) {
            this.searchDao = searchDao;
        }

        @Override
        protected Void doInBackground(Search... searches) {
            searchDao.insert(searches[0]);
            return null;
        }
    }

    private static class DeleteSearchAsyncTask extends AsyncTask<Search, Void, Void> {
        private SearchDao searchDao;

        private DeleteSearchAsyncTask(SearchDao searchDao) {
            this.searchDao = searchDao;
        }

        @Override
        protected Void doInBackground(Search... searches) {
            searchDao.delete(searches[0]);
            return null;
        }
    }

    private static class DeleteAllSearchAsyncTask extends AsyncTask<Void, Void, Void> {
        private SearchDao searchDao;

        private DeleteAllSearchAsyncTask(SearchDao searchDao) {
            this.searchDao = searchDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            searchDao.deleteAllData();
            return null;
        }
    }

    private static class SelectSearchAsyncTask extends AsyncTask<String, Void, String> {
        private SearchDao searchDao;

        private SelectSearchAsyncTask(SearchDao searchDao) {
            this.searchDao = searchDao;
        }

        @Override
        protected String doInBackground(String... strings) {
            return searchDao.getSearch(strings[0]);
        }
    }
}


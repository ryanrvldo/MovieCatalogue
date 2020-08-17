package com.ryanrvldo.moviecatalogue.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ryanrvldo.moviecatalogue.data.source.local.LocalRepository;
import com.ryanrvldo.moviecatalogue.data.source.model.Movie;
import com.ryanrvldo.moviecatalogue.data.source.model.Search;
import com.ryanrvldo.moviecatalogue.data.source.model.Season;
import com.ryanrvldo.moviecatalogue.data.source.model.TvShow;
import com.ryanrvldo.moviecatalogue.data.source.remote.LoadDataCallback;
import com.ryanrvldo.moviecatalogue.data.source.remote.RemoteRepository;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.MovieResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.TvShowResponse;

import java.util.List;

public class Repository implements DataSource {

    private volatile static Repository mInstance = null;
    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;

    private Repository(@NonNull RemoteRepository remoteRepository,
                       @NonNull LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public static Repository getInstance(RemoteRepository remoteData, LocalRepository localData) {
        if (mInstance == null) {
            synchronized (Repository.class) {
                if (mInstance == null) {
                    mInstance = new Repository(remoteData, localData);
                }
            }
        }
        return mInstance;
    }

    @Override
    public LiveData<MovieResponse> getMovies(String category) {
        MutableLiveData<MovieResponse> response = new MutableLiveData<>();
        remoteRepository.getMovies(category, new LoadDataCallback<MovieResponse>() {
            @Override
            public void onDataReceived(MovieResponse data) {
                response.postValue(data);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("GetMovies", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<MovieResponse> searchMovies(String query) {
        MutableLiveData<MovieResponse> response = new MutableLiveData<>();
        remoteRepository.searchMovies(query, new LoadDataCallback<MovieResponse>() {
            @Override
            public void onDataReceived(MovieResponse movieResponse) {
                response.postValue(movieResponse);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("SearchMovies", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<MovieResponse> getNewReleaseMovies() {
        MutableLiveData<MovieResponse> response = new MutableLiveData<>();
        remoteRepository.getNewReleaseMovies(new LoadDataCallback<MovieResponse>() {
            @Override
            public void onDataReceived(MovieResponse movieResponse) {
                response.postValue(movieResponse);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("NewReleaseMovies", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<Movie> getMovieDetail(int movieId) {
        MutableLiveData<Movie> response = new MutableLiveData<>();
        remoteRepository.getMovieDetail(movieId, new LoadDataCallback<Movie>() {
            @Override
            public void onDataReceived(Movie movie) {
                response.postValue(movie);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("GetMovieDetail", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<TvShowResponse> getTvShows(String category) {
        MutableLiveData<TvShowResponse> response = new MutableLiveData<>();
        remoteRepository.getTvShows(category, new LoadDataCallback<TvShowResponse>() {
            @Override
            public void onDataReceived(TvShowResponse tvShowResponse) {
                response.postValue(tvShowResponse);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("GetTvShows", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<TvShowResponse> searchTvShows(String query) {
        MutableLiveData<TvShowResponse> response = new MutableLiveData<>();
        remoteRepository.searchTvShows(query, new LoadDataCallback<TvShowResponse>() {
            @Override
            public void onDataReceived(TvShowResponse tvShowResponse) {
                response.postValue(tvShowResponse);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("SearchTvShows", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<TvShowResponse> getNewReleaseTvShows() {
        MutableLiveData<TvShowResponse> response = new MutableLiveData<>();
        remoteRepository.getNewReleaseTvShows(new LoadDataCallback<TvShowResponse>() {
            @Override
            public void onDataReceived(TvShowResponse tvShowResponse) {
                response.postValue(tvShowResponse);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("NewReleaseTvShows", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<TvShow> getTvDetail(int tvShowId) {
        MutableLiveData<TvShow> response = new MutableLiveData<>();
        remoteRepository.getTvDetail(tvShowId, new LoadDataCallback<TvShow>() {
            @Override
            public void onDataReceived(TvShow tvShow) {
                response.postValue(tvShow);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("GetTvDetail", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<Season> getSeasonDetail(int tvId, int seasonNumber) {
        MutableLiveData<Season> response = new MutableLiveData<>();
        remoteRepository.getSeasonDetail(tvId, seasonNumber, new LoadDataCallback<Season>() {
            @Override
            public void onDataReceived(Season season) {
                response.postValue(season);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("GetSeasons", "onDataNotAvailable");
            }
        });
        return response;
    }

    @Override
    public LiveData<List<Movie>> getFavMovies() {
        return localRepository.getAllFavMovies();
    }

    @Override
    public void insertFavMovie(Movie movie) {
        localRepository.insertMovie(movie);
    }

    @Override
    public void deleteFavMovie(Movie movie) {
        localRepository.deleteMovie(movie);
    }

    @Override
    public Movie getFavMovie(int movieId) {
        return localRepository.getMovie(movieId);
    }

    @Override
    public LiveData<List<TvShow>> getFavTvShows() {
        return localRepository.getAllFavTv();
    }

    @Override
    public void insertFavTv(TvShow tvShow) {
        localRepository.insertTv(tvShow);
    }

    @Override
    public void deleteFavTv(TvShow tvShow) {
        localRepository.deleteTv(tvShow);
    }

    @Override
    public TvShow getFavTv(int tvShowId) {
        return localRepository.getTvShow(tvShowId);
    }

    @Override
    public LiveData<List<Search>> getSearchHistory() {
        return localRepository.getAllSearch();
    }

    @Override
    public void insertSearch(Search search) {
        localRepository.insertSearch(search);
    }

    @Override
    public void deleteSearch(Search search) {
        localRepository.deleteSearch(search);
    }

    @Override
    public void deleteAllSearchHistory() {
        localRepository.deleteAllSearch();
    }

    @Override
    public String getSearch(String query) {
        return localRepository.getSearch(query);
    }
}
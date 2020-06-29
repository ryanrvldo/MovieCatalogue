package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<MovieResponse> popularMovies;
    private MutableLiveData<MovieResponse> nowPlayingMovies;
    private MutableLiveData<MovieResponse> topRatedMovies;
    private MutableLiveData<MovieResponse> newReleaseMovies;

    private Repository repository = Repository.getInstance();

    public void setPopularMovies() {
        popularMovies = new MutableLiveData<>();
        popularMovies = repository.getMovies("popular");
        Log.d("PopularMovies", "Data Created");
    }

    public LiveData<MovieResponse> getPopularMovies() {
        if (popularMovies == null) {
            setPopularMovies();
        }
        return popularMovies;
    }

    public void setNowPlayingMovies() {
        nowPlayingMovies = new MutableLiveData<>();
        nowPlayingMovies = repository.getMovies("now_playing");
        Log.d("UpcomingMovies", "Data Created");
    }

    public LiveData<MovieResponse> getNowPlayingMovies() {
        if (nowPlayingMovies == null) {
            setNowPlayingMovies();
        }
        return nowPlayingMovies;
    }

    public void setTopRatedMovies() {
        topRatedMovies = new MutableLiveData<>();
        topRatedMovies = repository.getMovies("top_rated");
        Log.d("TopRatedMovies", "Data Created");
    }

    public LiveData<MovieResponse> getTopRated() {
        if (topRatedMovies == null) {
            setTopRatedMovies();
        }
        return topRatedMovies;
    }

    public void setNewReleaseMovies() {
        newReleaseMovies = new MutableLiveData<>();
        newReleaseMovies = repository.newReleaseMovies();
        Log.d("NewRelease", "Fetched");
    }

    public LiveData<MovieResponse> getNewReleaseMovies() {
        if (newReleaseMovies == null) {
            setNewReleaseMovies();
        }
        return newReleaseMovies;
    }
}

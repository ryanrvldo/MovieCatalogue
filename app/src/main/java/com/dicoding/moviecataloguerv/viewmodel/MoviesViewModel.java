package com.dicoding.moviecataloguerv.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.data.Repository;
import com.dicoding.moviecataloguerv.data.source.remote.response.MovieResponse;

public class MoviesViewModel extends ViewModel {

    private LiveData<MovieResponse> popularMovies;
    private LiveData<MovieResponse> nowPlayingMovies;
    private LiveData<MovieResponse> topRatedMovies;
    private LiveData<MovieResponse> newReleaseMovies;

    private Repository repository;

    public MoviesViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setPopularMovies() {
        popularMovies = new MutableLiveData<>();
        popularMovies = repository.getMovies("popular");
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
    }

    public LiveData<MovieResponse> getTopRated() {
        if (topRatedMovies == null) {
            setTopRatedMovies();
        }
        return topRatedMovies;
    }

    public void setNewReleaseMovies() {
        newReleaseMovies = new MutableLiveData<>();
        newReleaseMovies = repository.getNewReleaseMovies();
    }

    public LiveData<MovieResponse> getNewReleaseMovies() {
        if (newReleaseMovies == null) {
            setNewReleaseMovies();
        }
        return newReleaseMovies;
    }
}

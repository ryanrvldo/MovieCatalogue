package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.data.Repository;
import com.dicoding.moviecataloguerv.data.source.remote.response.TvShowResponse;

public class TvShowsViewModel extends ViewModel {

    private LiveData<TvShowResponse> popularTv;
    private LiveData<TvShowResponse> nowPlayingTv;
    private LiveData<TvShowResponse> topRatedTv;
    private LiveData<TvShowResponse> newReleaseMovies;

    private Repository repository;

    public TvShowsViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setPopularTv() {
        popularTv = new MutableLiveData<>();
        popularTv = repository.getTvShows("popular");
        Log.d("PopularTv", "Data Created");
    }

    public LiveData<TvShowResponse> getPopularTv() {
        if (popularTv == null) {
            setPopularTv();
        }
        return popularTv;
    }

    public void setOnAirTv() {
        nowPlayingTv = new MutableLiveData<>();
        nowPlayingTv = repository.getTvShows("on_the_air");
        Log.d("NowPlayingTv", "Data Created");
    }

    public LiveData<TvShowResponse> getOnAirTv() {
        if (nowPlayingTv == null) {
            setOnAirTv();
        }
        return nowPlayingTv;
    }

    public void setTopRatedTv() {
        topRatedTv = new MutableLiveData<>();
        topRatedTv = repository.getTvShows("top_rated");
        Log.d("TopRatedTv", "Data Created");
    }

    public LiveData<TvShowResponse> getTopRatedTv() {
        if (topRatedTv == null) {
            setTopRatedTv();
        }
        return topRatedTv;
    }

    public void setNewReleaseTv() {
        newReleaseMovies = new MutableLiveData<>();
        newReleaseMovies = repository.getNewReleaseTvShows();
        Log.d("NewRelease", "Fetched");
    }

    public LiveData<TvShowResponse> getNewReleaseTv() {
        if (newReleaseMovies == null) {
            setNewReleaseTv();
        }
        return newReleaseMovies;
    }
}

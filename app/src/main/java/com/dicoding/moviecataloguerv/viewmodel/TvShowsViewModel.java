package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.TvShowResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class TvShowsViewModel extends ViewModel {

    private MutableLiveData<TvShowResponse> popularTv;
    private MutableLiveData<TvShowResponse> nowPlayingTv;
    private MutableLiveData<TvShowResponse> topRatedTv;
    private MutableLiveData<TvShowResponse> newReleaseMovies;

    private Repository repository = Repository.getInstance();

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
        newReleaseMovies = repository.newReleaseTVShow();
        Log.d("NewRelease", "Fetched");
    }

    public LiveData<TvShowResponse> getNewReleaseTv() {
        if (newReleaseMovies == null) {
            setNewReleaseTv();
        }
        return newReleaseMovies;
    }
}

package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class TvShowsViewModel extends ViewModel {

    private MutableLiveData<TvShowResponse> tvShows;
    private MutableLiveData<GenresResponse> genres;
    private MutableLiveData<TvShowItems> itemsTv;
    private MutableLiveData<TrailerResponse> trailers;

    private Repository repository;

    public LiveData<TvShowResponse> getTvShows(String language) {
        if (tvShows == null) {
            tvShows = new MutableLiveData<>();
            repository = Repository.getInstance();
            tvShows = repository.getTvShows(language);
            Log.d("FragmentTvShows", "Created");
        }
        return tvShows;
    }

    public LiveData<GenresResponse> getGenres(String language) {
        if (genres == null) {
            genres = new MutableLiveData<>();
            repository = Repository.getInstance();
            genres = repository.getTvGenres(language);
        }
        return genres;
    }

    public LiveData<TvShowItems> getTvShowItems(int tvShowId, String language) {
        if (itemsTv == null) {
            itemsTv = new MutableLiveData<>();
            repository = Repository.getInstance();
            itemsTv = repository.getTvShowItems(tvShowId, language);
            Log.d("TvDetail", "Created!");
        }
        return itemsTv;
    }

    public LiveData<TrailerResponse> getTrailers(int tvShowId) {
        if (trailers == null) {
            trailers = new MutableLiveData<>();
            repository = Repository.getInstance();
            trailers = repository.getTvTrailers(tvShowId);
        }
        return trailers;
    }
}

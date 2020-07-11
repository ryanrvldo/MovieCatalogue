package com.dicoding.moviecataloguerv.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.data.Repository;
import com.dicoding.moviecataloguerv.data.source.model.TvShow;

public class TvShowDetailViewModel extends ViewModel {
    private static final String type = "tv";

    private LiveData<TvShow> tvShow;

    private Repository repository;

    public TvShowDetailViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<TvShow> getTvDetail(int tvShowId) {
        if (tvShow == null) {
            tvShow = new MutableLiveData<>();
            tvShow = repository.getTvDetail(tvShowId);
        }
        return tvShow;
    }

    public void insertFavTv(TvShow tvShow) {
        repository.insertFavTv(tvShow);
    }

    public TvShow getFavTv(int tvId) {
        return repository.getFavTv(tvId);
    }

    public void deleteFavTv(TvShow tvShow) {
        repository.deleteFavTv(tvShow);
    }
}
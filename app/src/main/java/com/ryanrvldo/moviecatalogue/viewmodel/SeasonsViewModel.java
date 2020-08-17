package com.ryanrvldo.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ryanrvldo.moviecatalogue.data.Repository;
import com.ryanrvldo.moviecatalogue.data.source.model.Season;

public class SeasonsViewModel extends ViewModel {
    private LiveData<Season> seasons;

    private Repository repository;

    public SeasonsViewModel(Repository repository) {
        this.repository = repository;
    }

    private void setSeasons(int tvId, int seasonNumber) {
        seasons = new MutableLiveData<>();
        seasons = repository.getSeasonDetail(tvId, seasonNumber);
    }

    public LiveData<Season> getSeasons(int tvId, int seasonNumber) {
        if (seasons == null) {
            setSeasons(tvId, seasonNumber);
        }
        return seasons;
    }
}

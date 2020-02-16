package com.dicoding.moviecataloguerv.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.Season;
import com.dicoding.moviecataloguerv.network.Repository;

public class SeasonsViewModel extends ViewModel {
    private MutableLiveData<Season> seasons;

    private Repository repository = Repository.getInstance();

    private void setSeasons(int tvId, int seasonNumber) {
        seasons = new MutableLiveData<>();
        seasons = repository.getSeasons(tvId, seasonNumber);
    }

    public LiveData<Season> getSeasons(int tvId, int seasonNumber) {
        if (seasons == null) {
            setSeasons(tvId, seasonNumber);
        }
        return seasons;
    }
}

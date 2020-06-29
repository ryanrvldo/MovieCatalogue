package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.CreditsResponse;
import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShow;
import com.dicoding.moviecataloguerv.network.Repository;

public class TvShowDetailViewModel extends ViewModel {
    private MutableLiveData<GenresResponse> genres;
    private MutableLiveData<TvShow> itemsTv;
    private MutableLiveData<TrailerResponse> trailers;
    private MutableLiveData<CreditsResponse> credits;
    private MutableLiveData<SimilarResponse> similar;

    private Repository repository = Repository.getInstance();
    private static final String type = "tv";

    public LiveData<GenresResponse> getGenres() {
        if (genres == null) {
            genres = new MutableLiveData<>();
            genres = repository.getGenres(type);
        }
        return genres;
    }

    public LiveData<TvShow> getTvShowItems(int tvShowId) {
        if (itemsTv == null) {
            itemsTv = new MutableLiveData<>();
            itemsTv = repository.getTvShowItems(tvShowId);
            Log.d("TvDetail", "Data Created");
        }
        return itemsTv;
    }

    public LiveData<TrailerResponse> getTrailers(int tvShowId) {
        if (trailers == null) {
            trailers = new MutableLiveData<>();
            trailers = repository.getTrailers(type, tvShowId);
        }
        return trailers;
    }

    public LiveData<CreditsResponse> getCredits(int tvShowId) {
        if (credits == null) {
            credits = new MutableLiveData<>();
            credits = repository.getCredits(type, tvShowId);
        }
        return credits;
    }

    public LiveData<SimilarResponse> getSimilar(int tvShowId) {
        if (similar == null) {
            similar = new MutableLiveData<>();
            similar = repository.getSimilar(type, tvShowId, "similar");
        }
        return similar;
    }
}

package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.GenresResponse;
import com.dicoding.moviecataloguerv.model.SimilarResponse;
import com.dicoding.moviecataloguerv.model.TrailerResponse;
import com.dicoding.moviecataloguerv.model.TvShowItems;
import com.dicoding.moviecataloguerv.model.TvShowResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class TvShowsViewModel extends ViewModel {

    private MutableLiveData<TvShowResponse> popularTv;
    private MutableLiveData<TvShowResponse> upcomingTv;
    private MutableLiveData<TvShowResponse> topRatedTv;
    private MutableLiveData<GenresResponse> genres;
    private MutableLiveData<TvShowItems> itemsTv;
    private MutableLiveData<TrailerResponse> trailers;
    private MutableLiveData<SimilarResponse> similar;

    private Repository repository = Repository.getInstance();
    private String type = "tv";

    public void setPopularTv(String language) {
        popularTv = new MutableLiveData<>();
        popularTv = repository.getTvShows("popular", language);
        Log.d("FragmentPopularTvShows", "Data Created");
    }

    public LiveData<TvShowResponse> getPopularTv(String language) {
        if (popularTv == null) {
            setPopularTv(language);
        }
        return popularTv;
    }

    public void setUpcomingTv(String language) {
        upcomingTv = new MutableLiveData<>();
        upcomingTv = repository.getTvShows("airing_today", language);
        Log.d("FragmentUpcomingTvShows", "Data Created");
    }

    public LiveData<TvShowResponse> getUpcomingTv(String language) {
        if (upcomingTv == null) {
            setUpcomingTv(language);
        }
        return upcomingTv;
    }

    public void setTopRatedTv(String language) {
        topRatedTv = new MutableLiveData<>();
        topRatedTv = repository.getTvShows("top_rated", language);
        Log.d("FragmentTopRatedTvShows", "Data Created");
    }

    public LiveData<TvShowResponse> getTopRatedTv(String language) {
        if (topRatedTv == null) {
            setTopRatedTv(language);
        }
        return topRatedTv;
    }

    public void setGenres(String language) {
        genres = new MutableLiveData<>();
        genres = repository.getGenres(type, language);
    }

    public LiveData<GenresResponse> getGenres(String language) {
        if (genres == null) {
            setGenres(language);
        }
        return genres;
    }

    public LiveData<TvShowItems> getTvShowItems(int tvShowId, String language) {
        if (itemsTv == null) {
            itemsTv = new MutableLiveData<>();
            itemsTv = repository.getTvShowItems(tvShowId, language);
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

    public LiveData<SimilarResponse> getSimilar(int tvShowId) {
        if (similar == null) {
            similar = new MutableLiveData<>();
            similar = repository.getSimilar(type, tvShowId);
        }
        return similar;
    }
}

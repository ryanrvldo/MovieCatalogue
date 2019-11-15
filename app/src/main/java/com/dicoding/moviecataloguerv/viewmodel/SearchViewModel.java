package com.dicoding.moviecataloguerv.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.moviecataloguerv.model.MovieResponse;
import com.dicoding.moviecataloguerv.model.TvShowResponse;
import com.dicoding.moviecataloguerv.network.Repository;

public class SearchViewModel extends ViewModel {

    private LiveData<MovieResponse> searchMovies;
    private LiveData<TvShowResponse> searchTv;

    private Repository repository = Repository.getInstance();

    public void setSearchMovies(String query) {
        searchMovies = new MutableLiveData<>();
        searchMovies = repository.searchMovies(query);
        Log.d("SearchMovie", "Fetched");
    }

    public LiveData<MovieResponse> getSearchMovies(String query) {
        if (searchMovies == null) {
            setSearchMovies(query);
        }
        return searchMovies;
    }

    public void setSearchTv(String query) {
        searchTv = new MutableLiveData<>();
        searchTv = repository.searchTvShows(query);
        Log.d("SearchTv", "DataFetched");
    }

    public LiveData<TvShowResponse> getSearchTv(String query) {
        if (searchTv == null) {
            setSearchTv(query);
        }
        return searchTv;
    }
}

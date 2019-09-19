package com.dicoding.moviecataloguerv.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {

    private static final String API_KEY = "79cfc0b909c3e2a8083827dc3a084234";
    private MutableLiveData<MovieItems> listMovie = new MutableLiveData<>();

    void setMovie(final String language) {

    }

    MutableLiveData<MovieItems> getMovie() {
        return listMovie;
    }
}

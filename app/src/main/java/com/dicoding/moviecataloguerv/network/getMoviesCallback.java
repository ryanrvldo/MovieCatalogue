package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.Movie;

import java.util.List;

public interface getMoviesCallback {
    void onSuccess(List<Movie> movies);

    void onError();

}


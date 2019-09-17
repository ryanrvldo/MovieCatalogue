package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.Movie;

public interface onGetMovieCallback {

    void onSuccess(Movie movie);

    void onError();
}

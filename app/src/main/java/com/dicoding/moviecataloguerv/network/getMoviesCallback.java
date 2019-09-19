package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.MovieItems;

import java.util.List;

public interface getMoviesCallback {
    void onSuccess(List<MovieItems> movieItems);

    void onError();

}


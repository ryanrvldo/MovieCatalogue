package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.MovieItems;

import java.util.ArrayList;

public interface getMoviesCallback {
    void onSuccess(ArrayList<MovieItems> movieItems);

    void onError();

}


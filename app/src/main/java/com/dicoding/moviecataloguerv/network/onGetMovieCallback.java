package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.MovieItems;

public interface onGetMovieCallback {

    void onSuccess(MovieItems movieItems);

    void onError();
}

package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.Genre;

import java.util.ArrayList;

public interface getGenresCallback {
    void onSuccess(ArrayList<Genre> genres);

    void onError();
}

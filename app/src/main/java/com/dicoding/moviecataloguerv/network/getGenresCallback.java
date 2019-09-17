package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.Genre;

import java.util.List;

public interface getGenresCallback {
    void onSuccess(List<Genre> genres);

    void onError();
}

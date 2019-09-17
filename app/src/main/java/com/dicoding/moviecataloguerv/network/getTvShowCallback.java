package com.dicoding.moviecataloguerv.network;


import com.dicoding.moviecataloguerv.model.TvShow;

import java.util.List;

public interface getTvShowCallback {
    void onSuccess(List<TvShow> tvShows);

    void onError();
}

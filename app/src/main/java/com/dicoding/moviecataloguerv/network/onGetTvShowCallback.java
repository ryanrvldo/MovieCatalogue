package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.TvShow;

public interface onGetTvShowCallback {

    void onSuccess(TvShow tvShow);

    void onError();
}

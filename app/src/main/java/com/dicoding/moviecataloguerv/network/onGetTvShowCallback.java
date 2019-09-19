package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.TvShowItems;

public interface onGetTvShowCallback {

    void onSuccess(TvShowItems tvShowItems);

    void onError();
}

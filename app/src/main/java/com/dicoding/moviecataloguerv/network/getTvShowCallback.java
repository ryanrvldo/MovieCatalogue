package com.dicoding.moviecataloguerv.network;


import com.dicoding.moviecataloguerv.model.TvShowItems;

import java.util.List;

public interface getTvShowCallback {
    void onSuccess(List<TvShowItems> tvShowItems);

    void onError();
}

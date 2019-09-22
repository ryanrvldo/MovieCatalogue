package com.dicoding.moviecataloguerv.network;


import com.dicoding.moviecataloguerv.model.TvShowItems;

import java.util.ArrayList;

public interface getTvShowCallback {
    void onSuccess(ArrayList<TvShowItems> tvShowItems);

    void onError();
}

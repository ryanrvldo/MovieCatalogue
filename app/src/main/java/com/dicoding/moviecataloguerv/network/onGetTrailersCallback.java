package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.Trailer;

import java.util.ArrayList;

public interface onGetTrailersCallback {
    void onSuccess(ArrayList<Trailer> trailers);

    void onError();
}

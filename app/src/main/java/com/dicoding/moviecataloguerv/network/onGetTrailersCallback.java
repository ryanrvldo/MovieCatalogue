package com.dicoding.moviecataloguerv.network;

import com.dicoding.moviecataloguerv.model.Trailer;

import java.util.List;

public interface onGetTrailersCallback {
    void onSuccess(List<Trailer> trailers);

    void onError();
}

package com.ryanrvldo.moviecatalogue.data.source.remote;

public interface LoadDataCallback<T> {
    void onDataReceived(T data);

    void onDataNotAvailable();
}

package com.ryanrvldo.moviecatalogue.data.remote

interface LoadDataCallback<T> {
    fun onDataReceived(data: T)
    fun onDataNotAvailable()
}
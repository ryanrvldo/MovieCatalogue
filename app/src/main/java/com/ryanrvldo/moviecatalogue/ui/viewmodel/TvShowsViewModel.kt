package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.remote.response.TvShowResponse
import com.ryanrvldo.moviecatalogue.data.vo.Resource

class TvShowsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val popularTv: LiveData<Resource<TvShowResponse>> by lazy {
        repository.getTvShows("popular")
    }

    val nowPlayingTv: LiveData<Resource<TvShowResponse>> by lazy {
        repository.getTvShows("on_the_air")
    }

    val topRatedTv: LiveData<Resource<TvShowResponse>> by lazy {
        repository.getTvShows("top_rated")
    }
    val newReleaseTvShows: LiveData<TvShowResponse> by lazy {
        repository.getNewReleaseTvShows()
    }

    companion object {
        private const val TAG = "TvShowsVM"
    }
}
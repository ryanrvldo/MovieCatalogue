package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.remote.response.TvShowResponse
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val popularTv: LiveData<Resource<TvShowResponse>> by lazy {
        repository.getTvShows(Constants.POPULAR_CATEGORY)
    }

    val nowPlayingTv: LiveData<Resource<TvShowResponse>> by lazy {
        repository.getTvShows(Constants.ON_AIR_CATEGORY)
    }

    val topRatedTv: LiveData<Resource<TvShowResponse>> by lazy {
        repository.getTvShows(Constants.TOP_RATED_CATEGORY)
    }
    val newReleaseTvShows: LiveData<TvShowResponse> by lazy {
        repository.getNewReleaseTvShows()
    }

    companion object {
        private const val TAG = "TvShowsVM"
    }

}

package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.remote.response.MovieResponse
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.utils.Constants.NOW_PLAYING_CATEGORY
import com.ryanrvldo.moviecatalogue.utils.Constants.POPULAR_CATEGORY
import com.ryanrvldo.moviecatalogue.utils.Constants.TOP_RATED_CATEGORY
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val popularMovies: LiveData<Resource<MovieResponse>> by lazy {
        Timber.d("get popular movies")
        repository.getMovies(POPULAR_CATEGORY)
    }

    val nowPlayingMovies: LiveData<Resource<MovieResponse>> by lazy {
        Timber.d("get now playing movies")
        repository.getMovies(NOW_PLAYING_CATEGORY)
    }

    val topRatedMovies: LiveData<Resource<MovieResponse>> by lazy {
        Timber.d("get top rated movies")
        repository.getMovies(TOP_RATED_CATEGORY)
    }

    val newReleaseMovies: LiveData<MovieResponse> by lazy {
        Timber.d("get new release movies")
        repository.getNewReleaseMovies()
    }

}

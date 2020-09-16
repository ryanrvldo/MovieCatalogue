package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.remote.response.MovieResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.TvShowResponse
import com.ryanrvldo.moviecatalogue.data.vo.Resource

class SearchViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var query = savedStateHandle[SEARCH_QUERY_KEY] ?: ""

    val searchMovies: LiveData<Resource<MovieResponse>> by lazy {
        repository.searchMovies(query)
    }

    val searchTvShows: LiveData<Resource<TvShowResponse>> by lazy {
        repository.searchTvShows(query)
    }

    companion object {
        const val TAG = "SearchVM"
        const val SEARCH_QUERY_KEY = "SEARCH_QUERY"
    }
}
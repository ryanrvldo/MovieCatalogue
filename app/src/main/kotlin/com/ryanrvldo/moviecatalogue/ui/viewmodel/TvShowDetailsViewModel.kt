package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.lifecycle.*
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {

    private var tvShowId = savedStateHandle[TV_SHOW_ID_KEY] ?: 0

    val tvShow: LiveData<Resource<TvShow>> by lazy {
        repository.getTvShowDetails(tvShowId)
    }

    val isFavorites: LiveData<Boolean> = liveData {
        emit(repository.isFavoriteMovie(tvShowId))
    }

    fun addFavoriteTvShow(tvShow: TvShow) = viewModelScope.launch(Dispatchers.IO) {
        repository.addFavoriteTvShow(tvShow)
    }

    fun removeFavoriteTvShow(tvShow: TvShow) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeFavoriteTvShow(tvShow)
    }

    companion object {
        private const val TAG = "TvDetailsVM"
        const val TV_SHOW_ID_KEY = "TV_SHOW_ID"
    }
}

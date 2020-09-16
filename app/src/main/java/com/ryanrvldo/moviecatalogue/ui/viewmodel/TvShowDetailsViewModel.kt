package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvShowDetailsViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var tvShowId = savedStateHandle[TV_SHOW_ID_KEY] ?: 0

    val tvShow: LiveData<Resource<TvShow>> by lazy {
        repository.getTvShowDetails(tvShowId)
    }

    //    private val _isFavorites = MutableLiveData<Boolean>()
    val isFavorites: LiveData<Boolean> = liveData {
        emit(repository.isFavoriteMovie(tvShowId))
    }
//
//    private fun checkMovieFavorite() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _isFavorites.postValue(repository.isFavoriteTvShow(tvShowId))
//        }
//    }

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
package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.lifecycle.*
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel() {

    private var movieId = savedStateHandle[MOVIE_ID_KEY] ?: 0

    val movieDetails: LiveData<Resource<Movie>> by lazy {
        repository.getMovieDetails(movieId)
    }

    val isFavorites: LiveData<Boolean> = liveData {
        emit(repository.isFavoriteMovie(movieId))
    }

    fun addFavoriteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.addFavoriteMovie(movie)
    }

    fun deleteFavMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeFavoriteMovie(movie)
    }

    companion object {
        const val TAG = "MovieDetailsVM"
        const val MOVIE_ID_KEY = "MOVIE_ID"
    }

}

package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.Movie
import com.ryanrvldo.moviecatalogue.data.model.TvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val favoriteMovies: LiveData<List<Movie>> by lazy {
        repository.getFavoriteMovies()
    }

    val favoriteTvShows: LiveData<List<TvShow>> by lazy {
        repository.getFavoriteTvShows()
    }

    fun deleteFavMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeFavoriteMovie(movie)
    }

    fun deleteFavTv(tvShow: TvShow) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeFavoriteTvShow(tvShow)
    }
}
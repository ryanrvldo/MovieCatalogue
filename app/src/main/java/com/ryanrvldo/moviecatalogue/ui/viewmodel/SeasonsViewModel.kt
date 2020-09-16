package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.Season

class SeasonsViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var tvShowId = savedStateHandle.get<Int>(TV_ID_KEY) ?: 0
    private var seasonNumber = savedStateHandle.get<Int>(SEASONS_KEY) ?: 0

    val seasons: LiveData<Season> by lazy {
        repository.getSeasonDetails(tvShowId, seasonNumber)
    }

    companion object {
        const val TAG = "SeasonsVM"
        const val TV_ID_KEY = "TV_SHOW_ID"
        const val SEASONS_KEY = "SEASON_NUMBER"
    }
}
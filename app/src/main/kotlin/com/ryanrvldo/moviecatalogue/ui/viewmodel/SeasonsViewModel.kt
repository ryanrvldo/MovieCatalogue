package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.Season
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository,
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

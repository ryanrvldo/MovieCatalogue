package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    val searchHistories: LiveData<List<Search>> by lazy {
        repository.getSearchHistories()
    }

    fun addSearchQuery(search: Search) = viewModelScope.launch(Dispatchers.IO) {
        repository.addSearchQuery(search)
    }

    fun removeSearchQuery(search: Search) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeSearchQuery(search)
    }

    fun removeSearchHistories() = viewModelScope.launch(Dispatchers.IO) {
        repository.removeSearchHistories()
    }

    suspend fun selectSearch(query: String): String? = repository.selectSearchQuery(query)

}

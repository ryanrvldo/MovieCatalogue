package com.ryanrvldo.moviecatalogue.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanrvldo.moviecatalogue.data.Repository
import com.ryanrvldo.moviecatalogue.data.model.Search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentSearchViewModel @ViewModelInject constructor(
    private val repository: Repository
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
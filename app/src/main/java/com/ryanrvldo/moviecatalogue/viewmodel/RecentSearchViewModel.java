package com.ryanrvldo.moviecatalogue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ryanrvldo.moviecatalogue.data.Repository;
import com.ryanrvldo.moviecatalogue.data.source.model.Search;

import java.util.List;

public class RecentSearchViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Search>> searchQuery;

    public RecentSearchViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Search>> getSearchQuery() {
        if (searchQuery == null) {
            searchQuery = new MutableLiveData<>();
            searchQuery = repository.getSearchHistory();
        }
        return searchQuery;
    }

    public void insertSearch(Search search) {
        repository.insertSearch(search);
        searchQuery = repository.getSearchHistory();
    }

    public void deleteSearch(Search search) {
        repository.deleteSearch(search);
        searchQuery = repository.getSearchHistory();
    }

    public void deleteAllSearch() {
        repository.deleteAllSearchHistory();
        searchQuery = repository.getSearchHistory();
    }

    public String selectSearch(String query) {
        return repository.getSearch(query);
    }
}

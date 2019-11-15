package com.dicoding.moviecataloguerv.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.moviecataloguerv.model.Search;
import com.dicoding.moviecataloguerv.network.Repository;

import java.util.List;

public class RecentSearchViewModel extends AndroidViewModel {
    private static final String TAG = "SearchQuery";

    private Repository repository;
    private LiveData<List<Search>> searchQuery;

    public RecentSearchViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Search>> getSearchQuery() {
        if (searchQuery == null) {
            searchQuery = new MutableLiveData<>();
            searchQuery = repository.getAllSearch();
        }
        return searchQuery;
    }

    public void addSearch(Search search) {
        repository.addSearch(search);
        searchQuery = repository.getAllSearch();
        Log.d(TAG, "addSearch: success");
    }

    public void deleteSearch(Search search) {
        repository.deleteSearch(search);
        searchQuery = repository.getAllSearch();
        Log.d(TAG, "deleteSearch: success");
    }

    public void deleteAllSearch() {
        repository.deleteAllSearch();
        searchQuery = repository.getAllSearch();
        Log.d(TAG, "deleteAllSearch: success");
    }

    public String selectSearch(String query) {
        Log.d(TAG, "selectSearch: selected");
        return repository.getSearch(query);
    }
}

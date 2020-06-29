package com.dicoding.moviecataloguerv.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dicoding.moviecataloguerv.model.Search;

import java.util.List;

@Dao
public interface SearchDao {

    @Insert
    void insert(Search search);

    @Delete
    void delete(Search search);

    @Query("SELECT * FROM query_search ORDER BY id DESC")
    LiveData<List<Search>> getAllSearch();

    @Query("SELECT `query` FROM query_search WHERE `query`=:query")
    String getSearch(String query);

    @Query("DELETE FROM query_search")
    void deleteAllData();
}

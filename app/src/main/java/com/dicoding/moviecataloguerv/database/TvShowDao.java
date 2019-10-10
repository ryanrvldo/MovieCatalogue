package com.dicoding.moviecataloguerv.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dicoding.moviecataloguerv.model.TvShowItems;

import java.util.List;

@Dao
public interface TvShowDao {

    @Insert
    void insert(TvShowItems tvShowItems);

    @Delete
    void delete(TvShowItems tvShowItems);

    @Query("SELECT * FROM favorite_tv")
    LiveData<List<TvShowItems>> getAllFavoriteTv();

    @Query("SELECT * FROM favorite_tv WHERE id=:id")
    TvShowItems selectById(int id);
}

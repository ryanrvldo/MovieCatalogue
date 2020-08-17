package com.ryanrvldo.moviecatalogue.data.source.local.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ryanrvldo.moviecatalogue.data.source.model.TvShow;

import java.util.List;

@Dao
public interface TvShowDao {

    @Insert
    void insert(TvShow tvShow);

    @Delete
    void delete(TvShow tvShow);

    @Query("SELECT * FROM favorite_tv")
    LiveData<List<TvShow>> getAllFavoriteTv();

    @Query("SELECT * FROM favorite_tv WHERE id=:id")
    TvShow selectById(int id);
}

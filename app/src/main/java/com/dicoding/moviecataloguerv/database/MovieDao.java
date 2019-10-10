package com.dicoding.moviecataloguerv.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dicoding.moviecataloguerv.model.MovieItems;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(MovieItems movieItems);

    @Delete
    void delete(MovieItems movieItems);

    @Query("SELECT * FROM favorite_movie ORDER BY title ASC")
    LiveData<List<MovieItems>> getAllFavoriteMovie();

    @Query("SELECT * FROM favorite_movie WHERE id=:id")
    MovieItems selectById(int id);
}

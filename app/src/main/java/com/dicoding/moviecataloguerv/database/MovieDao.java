package com.dicoding.moviecataloguerv.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dicoding.moviecataloguerv.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM favorite_movie ORDER BY title ASC")
    LiveData<List<Movie>> getAllFavoriteMovie();

    @Query("SELECT * FROM favorite_movie")
    List<Movie> getFavoriteMovies();

    @Query("SELECT * FROM favorite_movie WHERE id=:id")
    Movie selectById(int id);
}
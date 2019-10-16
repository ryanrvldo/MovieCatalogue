package com.dicoding.favoritemovieapp;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.favoritemovieapp.DatabaseContract.FavoriteMovieColumns.BACKDROP;
import static com.dicoding.favoritemovieapp.DatabaseContract.FavoriteMovieColumns.POSTER_PATH;
import static com.dicoding.favoritemovieapp.DatabaseContract.FavoriteMovieColumns.RELEASE_DATE;
import static com.dicoding.favoritemovieapp.DatabaseContract.FavoriteMovieColumns.TITLE;
import static com.dicoding.favoritemovieapp.DatabaseContract.FavoriteMovieColumns.VOTE_AVERAGE;

public class MappingHelper {
    public static ArrayList<MovieFavorite> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<MovieFavorite> favoriteArrayList = new ArrayList<>();
        while (movieCursor.moveToNext()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(_ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE));
            String releaseDate = movieCursor.getString(movieCursor.getColumnIndexOrThrow(RELEASE_DATE));
            int voteAverage = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            String backdrop = movieCursor.getString(movieCursor.getColumnIndexOrThrow(BACKDROP));
            String posterPath = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTER_PATH));
            favoriteArrayList.add(new MovieFavorite(id, title, posterPath, releaseDate, voteAverage, backdrop));
        }
        return favoriteArrayList;
    }
}
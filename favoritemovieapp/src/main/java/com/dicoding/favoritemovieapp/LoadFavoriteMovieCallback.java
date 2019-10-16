package com.dicoding.favoritemovieapp;

import android.database.Cursor;

public interface LoadFavoriteMovieCallback {
    void postExecute(Cursor cursor);
}

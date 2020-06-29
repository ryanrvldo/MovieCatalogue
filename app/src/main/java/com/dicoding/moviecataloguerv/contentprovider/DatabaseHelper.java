package com.dicoding.moviecataloguerv.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.FavoriteMovieColumns.TABLE_NAME,
            DatabaseContract.FavoriteMovieColumns._ID,
            DatabaseContract.FavoriteMovieColumns.TITLE,
            DatabaseContract.FavoriteMovieColumns.POSTER_PATH,
            DatabaseContract.FavoriteMovieColumns.RELEASE_DATE,
            DatabaseContract.FavoriteMovieColumns.VOTE_AVERAGE,
            DatabaseContract.FavoriteMovieColumns.BACKDROP
    );
    private static String DATABASE_NAME = "favorite_movie_database";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoriteMovieColumns.TABLE_NAME);
    }
}

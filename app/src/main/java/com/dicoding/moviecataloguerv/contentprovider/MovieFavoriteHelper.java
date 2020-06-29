package com.dicoding.moviecataloguerv.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.BACKDROP;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.POSTER_PATH;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.RELEASE_DATE;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.TABLE_NAME;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.TITLE;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.VOTE_AVERAGE;

public class MovieFavoriteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static MovieFavoriteHelper INSTANCE;
    private final DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private MovieFavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieFavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieFavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public ArrayList<MovieFavorite> query() {
        ArrayList<MovieFavorite> movieFavorites = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        MovieFavorite movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new MovieFavorite();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                movieFavorites.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieFavorites;
    }

    Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}

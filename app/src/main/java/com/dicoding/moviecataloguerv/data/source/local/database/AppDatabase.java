package com.dicoding.moviecataloguerv.data.source.local.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dicoding.moviecataloguerv.data.source.model.Movie;
import com.dicoding.moviecataloguerv.data.source.model.Search;
import com.dicoding.moviecataloguerv.data.source.model.TvShow;


@Database(entities = {Movie.class, TvShow.class, Search.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public abstract TvShowDao tvShowDao();

    public abstract SearchDao searchDao();

    private static volatile AppDatabase instance = null;

    @NonNull
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "movie_catalogue_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}

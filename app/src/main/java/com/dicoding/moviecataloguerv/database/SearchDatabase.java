package com.dicoding.moviecataloguerv.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dicoding.moviecataloguerv.model.Search;

@Database(entities = {Search.class}, version = 1)
public abstract class SearchDatabase extends RoomDatabase {

    private static SearchDatabase instance;

    public static synchronized SearchDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SearchDatabase.class, "search_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract SearchDao searchDao();
}

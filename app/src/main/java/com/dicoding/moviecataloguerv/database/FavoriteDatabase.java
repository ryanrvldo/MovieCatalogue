package com.dicoding.moviecataloguerv.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dicoding.moviecataloguerv.model.MovieItems;
import com.dicoding.moviecataloguerv.model.TvShowItems;


@Database(entities = {MovieItems.class, TvShowItems.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;
    private static RoomDatabase.Callback initCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitAsyncTask(instance).execute();
        }
    };

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoriteDatabase.class, "favorite_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(initCallback)
                    .build();
        }
        return instance;
    }

    public abstract MovieDao movieDao();

    public abstract TvShowDao tvShowDao();

    private static class InitAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDao movieDao;
        private TvShowDao tvShowDao;

        InitAsyncTask(FavoriteDatabase db) {
            movieDao = db.movieDao();
            tvShowDao = db.tvShowDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.insert(new MovieItems(408,
                    "Snow White and the Seven Dwarfs",
                    "/bOtgcOIFBCUFdY2a737Na6gWQ0X.jpg",
                    "1937-12-21",
                    7,
                    "/c66otZnSdri67kR7ps92kRX849o.jpg"
            ));

            tvShowDao.insert(new TvShowItems(1418,
                    "The Big Bang Theory",
                    "/ooBGRQBdbGzBxAVfExiO8r7kloA.jpg",
                    "2007-09-24",
                    (float) 6.8,
                    "/nGsNruW3W27V6r4gkyc3iiEGsKR.jpg"
            ));
            return null;
        }
    }
}

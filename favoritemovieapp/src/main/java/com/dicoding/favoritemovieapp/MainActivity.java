package com.dicoding.favoritemovieapp;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.dicoding.favoritemovieapp.DatabaseContract.FavoriteMovieColumns.CONTENT_URI;
import static com.dicoding.favoritemovieapp.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadFavoriteMovieCallback {

    private FavoriteMoviesAdapter adapter;

    private DataObserver observer;
    private TextView textViewNull;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Favorite Movie App");
        }

        RecyclerView recyclerView = findViewById(R.id.rvMovies);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textViewNull = findViewById(R.id.item_null);

        adapter = new FavoriteMoviesAdapter(this);
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        observer = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, observer);
        new getData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor favoriteMovie) {
        ArrayList<MovieFavorite> listFavoriteMovie = mapCursorToArrayList(favoriteMovie);
        if (listFavoriteMovie.size() > 0) {
            adapter.refillMovie(listFavoriteMovie);
            textViewNull.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "There is no data now.", Toast.LENGTH_SHORT).show();
            adapter.refillMovie(new ArrayList<MovieFavorite>());
            textViewNull.setVisibility(View.VISIBLE);
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteMovieCallback> weakCallback;

        private getData(Context context, LoadFavoriteMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }

    static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}

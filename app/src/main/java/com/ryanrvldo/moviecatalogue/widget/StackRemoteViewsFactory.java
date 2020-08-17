package com.ryanrvldo.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.ryanrvldo.moviecatalogue.BuildConfig;
import com.ryanrvldo.moviecatalogue.R;
import com.ryanrvldo.moviecatalogue.data.source.local.database.AppDatabase;
import com.ryanrvldo.moviecatalogue.data.source.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ryanrvldo.moviecatalogue.widget.FavoriteMovieWidget.EXTRA_ITEM;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private static final String TAG = "Widget";

    private List<Movie> movieFavorite = new ArrayList<>();
    private AppDatabase database;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        database = AppDatabase.getInstance(mContext);
        try {
            movieFavorite = new GetFavMoviesAsyncTask(database).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDataSetChanged() {
        try {
            movieFavorite = new GetFavMoviesAsyncTask(database).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class GetFavMoviesAsyncTask extends AsyncTask<AppDatabase, Void, List<Movie>> {
        AppDatabase database;

        GetFavMoviesAsyncTask(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected List<Movie> doInBackground(AppDatabase... appDatabases) {
            return database.movieDao().getFavoriteMovies();
        }
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieFavorite.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        if (movieFavorite.size() > 0) {
            Movie movie = movieFavorite.get(position);

            try {
                Bitmap bitmap = Glide.with(mContext)
                        .asBitmap()
                        .load(BuildConfig.TMDB_IMAGE_342 + movie.getPosterPath())
                        .submit(512, 512)
                        .get();

                remoteViews.setImageViewBitmap(R.id.imageWidget, bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bundle extras = new Bundle();
            extras.putString(EXTRA_ITEM, movie.getTitle());
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            remoteViews.setOnClickFillInIntent(R.id.imageWidget, fillInIntent);
            Log.d(TAG, "Data is more than 0");
            return remoteViews;
        } else {
            Log.d(TAG, "Data is null");
            return null;
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
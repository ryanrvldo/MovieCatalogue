package com.dicoding.moviecataloguerv.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dicoding.moviecataloguerv.ui.favorite.FavoriteMovieFragment;

import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.AUTHORITY;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.CONTENT_URI;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns.TABLE_NAME;

public class MovieFavoriteProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);
    }

    private MovieFavoriteHelper movieFavoriteHelper;

    @Override
    public boolean onCreate() {
        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        movieFavoriteHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieFavoriteHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieFavoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        movieFavoriteHelper.open();
        long added;
        if (sUriMatcher.match(uri) == MOVIE) {
            added = movieFavoriteHelper.insertProvider(values);
        } else {
            added = 0;
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        movieFavoriteHelper.open();
        int updated;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            updated = movieFavoriteHelper.updateProvider(uri.getLastPathSegment(), values);
        } else {
            updated = 0;
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        movieFavoriteHelper.open();
        int deleted;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            deleted = movieFavoriteHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
        }
        return deleted;
    }
}

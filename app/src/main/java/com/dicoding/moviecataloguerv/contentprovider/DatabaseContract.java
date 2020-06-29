package com.dicoding.moviecataloguerv.contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {
    static final String AUTHORITY = "com.dicoding.moviecataloguerv";
    private static final String SCHEME = "content";

    private DatabaseContract() {
    }

    static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static final class FavoriteMovieColumns implements BaseColumns {
        static final String TABLE_NAME = "favorite_movie";
        public static final String _ID = "_id";
        public static final String TITLE = "title";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELEASE_DATE = "release_date";
        public static final String VOTE_AVERAGE = "rating";
        public static final String BACKDROP = "backdrop";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

}

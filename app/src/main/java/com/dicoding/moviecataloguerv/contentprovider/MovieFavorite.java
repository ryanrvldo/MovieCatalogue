package com.dicoding.moviecataloguerv.contentprovider;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.FavoriteMovieColumns;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.getColumnDouble;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.getColumnInt;
import static com.dicoding.moviecataloguerv.contentprovider.DatabaseContract.getColumnString;

public class MovieFavorite implements Parcelable {
    public static final Parcelable.Creator<MovieFavorite> CREATOR = new Parcelable.Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel source) {
            return new MovieFavorite(source);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };
    private int id;
    private String title;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;
    private String backdrop;

    public MovieFavorite() {
    }

    public MovieFavorite(int id, String title, String posterPath, String releaseDate, double voteAverage, String backdrop) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.backdrop = backdrop;
    }

    public MovieFavorite(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, FavoriteMovieColumns.TITLE);
        this.posterPath = getColumnString(cursor, FavoriteMovieColumns.POSTER_PATH);
        this.releaseDate = getColumnString(cursor, FavoriteMovieColumns.RELEASE_DATE);
        this.voteAverage = getColumnDouble(cursor, FavoriteMovieColumns.VOTE_AVERAGE);
        this.backdrop = getColumnString(cursor, FavoriteMovieColumns.BACKDROP);
    }

    private MovieFavorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.backdrop = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.backdrop);
    }
}

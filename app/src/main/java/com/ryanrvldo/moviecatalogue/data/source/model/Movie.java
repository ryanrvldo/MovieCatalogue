package com.ryanrvldo.moviecatalogue.data.source.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.CreditsResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.ImageResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.SimilarResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.VideosResponse;

import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = "favorite_movie")
public class Movie {
    @PrimaryKey
    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String overview;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("vote_average")
    @Expose
    private float rating;

    @Ignore
    @SerializedName("vote_count")
    @Expose
    private int ratingVotes;

    @SerializedName("backdrop_path")
    @Expose
    private String backdrop;

    @Ignore
    @SerializedName("runtime")
    @Expose
    private int duration;

    @Ignore
    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    @Ignore
    @Expose
    private ImageResponse images;

    @Ignore
    @Expose
    private VideosResponse videos;

    @Ignore
    @Expose
    private CreditsResponse credits;

    @Ignore
    @Expose
    private SimilarResponse similar;

    public Movie(int id, String title, String overview, String posterPath, String releaseDate, float rating, String backdrop) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.backdrop = backdrop;
    }
}

package com.ryanrvldo.moviecatalogue.data.source.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.ContentRatingResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.CreditsResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.ImageResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.SimilarResponse;
import com.ryanrvldo.moviecatalogue.data.source.remote.response.VideosResponse;

import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = "favorite_tv")
public class TvShow {
    @PrimaryKey
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String title;

    @Expose
    private String overview;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @ColumnInfo(name = "release_date")
    @SerializedName("first_air_date")
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
    @Expose
    private List<Genre> genres;

    @Ignore
    @Expose
    private List<Season> seasons;

    @Ignore
    @Expose
    private ImageResponse images;

    @Ignore
    @SerializedName("content_ratings")
    @Expose
    private ContentRatingResponse contentRatingResponse;

    @Ignore
    @Expose
    private VideosResponse videos;

    @Ignore
    @Expose
    private CreditsResponse credits;

    @Ignore
    @Expose
    private SimilarResponse similar;

    public TvShow(int id, String title, String overview, String posterPath, String releaseDate, float rating, String backdrop) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.backdrop = backdrop;
    }
}

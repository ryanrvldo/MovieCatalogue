package com.dicoding.moviecataloguerv.data.source.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class Season {
    @Expose
    private int id;

    @Expose
    private String name;

    @SerializedName("air_date")
    @Expose
    private String airDate;

    @Expose
    private String overview;

    @Expose
    private List<Episode> episodes;

    @SerializedName("episode_count")
    @Expose
    private int totalEpisode;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("season_number")
    @Expose
    private int seasonNumber;
}

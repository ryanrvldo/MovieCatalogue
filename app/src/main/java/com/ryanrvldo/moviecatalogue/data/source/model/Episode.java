package com.ryanrvldo.moviecatalogue.data.source.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Episode {
    @Expose
    private int id;

    @SerializedName("air_date")
    @Expose
    private String airDate;

    @SerializedName("episode_number")
    @Expose
    private int number;

    @Expose
    private String name;

    @Expose
    private String overview;

    @SerializedName("still_path")
    @Expose
    private String stillPath;
}

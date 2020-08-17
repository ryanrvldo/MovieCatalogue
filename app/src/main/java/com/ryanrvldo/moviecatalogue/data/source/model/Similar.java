package com.ryanrvldo.moviecatalogue.data.source.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Similar {
    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String name;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("vote_average")
    @Expose
    private float rating;
}

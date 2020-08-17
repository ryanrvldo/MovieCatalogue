package com.ryanrvldo.moviecatalogue.data.source.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Cast {
    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private String character;

    @Expose
    private int gender;

    @SerializedName("credit_id")
    @Expose
    private String creditId;

    @SerializedName("profile_path")
    @Expose
    private String profilePath;
}

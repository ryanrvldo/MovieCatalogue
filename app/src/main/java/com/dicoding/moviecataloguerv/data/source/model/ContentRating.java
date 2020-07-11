package com.dicoding.moviecataloguerv.data.source.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ContentRating {
    @SerializedName("iso_3166_1")
    @Expose
    private String iso;

    @Expose
    private String rating;
}

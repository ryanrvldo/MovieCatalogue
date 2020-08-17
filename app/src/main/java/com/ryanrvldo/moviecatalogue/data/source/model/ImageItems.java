package com.ryanrvldo.moviecatalogue.data.source.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ImageItems {
    @SerializedName("file_path")
    @Expose
    private String filePath;

    @SerializedName("vote_average")
    @Expose
    private double rating;
}

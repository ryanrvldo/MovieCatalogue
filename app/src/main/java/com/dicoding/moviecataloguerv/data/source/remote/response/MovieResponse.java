package com.dicoding.moviecataloguerv.data.source.remote.response;

import com.dicoding.moviecataloguerv.data.source.model.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class MovieResponse {
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private ArrayList<Movie> movieItems;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;
}

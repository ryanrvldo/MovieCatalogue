package com.dicoding.moviecataloguerv.data.source.remote.response;

import com.dicoding.moviecataloguerv.data.source.model.TvShow;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class TvShowResponse {
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private ArrayList<TvShow> tvShowItems;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;
}

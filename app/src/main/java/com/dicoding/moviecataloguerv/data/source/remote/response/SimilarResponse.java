package com.dicoding.moviecataloguerv.data.source.remote.response;

import com.dicoding.moviecataloguerv.data.source.model.Similar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SimilarResponse {
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<Similar> similar;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;
}

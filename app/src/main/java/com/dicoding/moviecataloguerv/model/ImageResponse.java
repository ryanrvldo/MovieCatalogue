package com.dicoding.moviecataloguerv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {

    @SerializedName("backdrops")
    @Expose
    private List<ImageItems> backdrops;

    @SerializedName("posters")
    @Expose
    private List<ImageItems> posters;

    public List<ImageItems> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<ImageItems> backdrops) {
        this.backdrops = backdrops;
    }

    public List<ImageItems> getPosters() {
        return posters;
    }

    public void setPosters(List<ImageItems> posters) {
        this.posters = posters;
    }
}

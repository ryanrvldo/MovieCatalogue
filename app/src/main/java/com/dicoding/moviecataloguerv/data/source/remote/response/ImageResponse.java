package com.dicoding.moviecataloguerv.data.source.remote.response;

import com.dicoding.moviecataloguerv.data.source.model.ImageItems;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ImageResponse {

    @SerializedName("backdrops")
    @Expose
    private List<ImageItems> backdrops;
}

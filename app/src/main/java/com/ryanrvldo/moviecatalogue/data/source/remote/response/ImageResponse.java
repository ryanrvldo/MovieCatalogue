package com.ryanrvldo.moviecatalogue.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryanrvldo.moviecatalogue.data.source.model.ImageItems;

import java.util.List;

import lombok.Data;

@Data
public class ImageResponse {

    @SerializedName("backdrops")
    @Expose
    private List<ImageItems> backdrops;
}

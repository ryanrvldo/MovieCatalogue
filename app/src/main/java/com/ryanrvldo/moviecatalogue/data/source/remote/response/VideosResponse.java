package com.ryanrvldo.moviecatalogue.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ryanrvldo.moviecatalogue.data.source.model.Video;

import java.util.List;

import lombok.Data;

@Data
public class VideosResponse {
    @SerializedName("results")
    @Expose
    private List<Video> videos;
}

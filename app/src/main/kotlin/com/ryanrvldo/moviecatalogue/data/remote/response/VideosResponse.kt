package com.ryanrvldo.moviecatalogue.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ryanrvldo.moviecatalogue.data.model.Video

data class VideosResponse(
    @SerializedName("results") @Expose val videos: List<Video>
)
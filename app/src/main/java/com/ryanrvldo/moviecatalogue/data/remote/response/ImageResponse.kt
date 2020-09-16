package com.ryanrvldo.moviecatalogue.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ryanrvldo.moviecatalogue.data.model.Image

data class ImageResponse(
    @Expose @SerializedName("backdrops") val backdrops: List<Image>
)
package com.ryanrvldo.core.data.source.remote.response.commons

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("backdrops") val backdrops: List<ImageResponse>?,
    @SerializedName("posters") val posters: List<ImageResponse>?,
)

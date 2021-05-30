package com.ryanrvldo.data.network.response.commons

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("aspect_ratio") val aspectRatio: Double?,
    @SerializedName("file_path") val filePath: String?,
    @SerializedName("vote_average") val rating: Double?,
    @SerializedName("vote_count") val ratingCount: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("width") val width: Int?,
)

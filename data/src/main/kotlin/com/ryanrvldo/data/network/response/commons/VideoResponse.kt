package com.ryanrvldo.data.network.response.commons

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("key") val key: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("site") val site: String?,
    @SerializedName("size") val size: Int?,
    @SerializedName("type") val type: String?,
)

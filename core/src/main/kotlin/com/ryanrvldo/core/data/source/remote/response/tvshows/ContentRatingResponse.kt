package com.ryanrvldo.core.data.source.remote.response.tvshows

import com.google.gson.annotations.SerializedName

data class ContentRatingResponse(
    @SerializedName("iso_3166_1") val iso: String?,
    @SerializedName("rating") val rating: String?,
)

package com.ryanrvldo.core.data.source.remote.response.commons

import com.google.gson.annotations.SerializedName

data class SimilarResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val rating: Double?,
)

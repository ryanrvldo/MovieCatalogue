package com.ryanrvldo.data.network.response.commons

import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("character") val character: String,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("order") val order: Int
)

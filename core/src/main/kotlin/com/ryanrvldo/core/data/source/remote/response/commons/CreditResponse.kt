package com.ryanrvldo.core.data.source.remote.response.commons

import com.google.gson.annotations.SerializedName

data class CreditResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("character") val character: String?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("profile_path") val profilePath: String?,
)

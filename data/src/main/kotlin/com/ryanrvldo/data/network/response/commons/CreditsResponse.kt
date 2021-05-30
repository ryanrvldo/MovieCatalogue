package com.ryanrvldo.data.network.response.commons

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("cast") val casts: List<CreditResponse>?,
    @SerializedName("crew") val crews: List<CreditsResponse>?,
)

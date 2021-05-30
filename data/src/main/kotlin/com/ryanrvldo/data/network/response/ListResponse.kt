package com.ryanrvldo.data.network.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<T>,
)

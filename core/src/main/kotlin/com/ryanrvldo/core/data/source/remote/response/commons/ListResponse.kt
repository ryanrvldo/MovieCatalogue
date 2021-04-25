package com.ryanrvldo.core.data.source.remote.response.commons

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("id") val id: Int?,
    @SerializedName("results") val results: List<T>?,
)

package com.ryanrvldo.data.network.response.tvshows

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode_number") val number: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("still_path") val stillPath: String?,
)

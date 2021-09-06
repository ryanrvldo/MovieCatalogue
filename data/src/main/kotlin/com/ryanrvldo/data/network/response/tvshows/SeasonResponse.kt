package com.ryanrvldo.data.network.response.tvshows

import com.google.gson.annotations.SerializedName

data class SeasonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("episode_count") val totalEpisodes: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("season_number") val number: Int,
)

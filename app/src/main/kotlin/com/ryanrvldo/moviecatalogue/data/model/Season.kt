package com.ryanrvldo.moviecatalogue.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Season(
    @Expose val id: Int,
    @Expose val name: String,
    @Expose @SerializedName("air_date") val airDate: String,
    @Expose val overview: String,
    @Expose val episodes: List<Episode>,
    @Expose @SerializedName("episode_count") val totalEpisodes: Int,
    @Expose @SerializedName("poster_path") val posterPath: String,
    @Expose @SerializedName("season_number") val number: Int
)
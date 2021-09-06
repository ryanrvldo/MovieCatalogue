package com.ryanrvldo.data.network.response.tvshows

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val releaseDate: String,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("vote_count") val ratingVotes: Int,
)

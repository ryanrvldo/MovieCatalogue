package com.ryanrvldo.data.network.response.movies

import com.google.gson.annotations.SerializedName
import com.ryanrvldo.data.network.response.ListResponse
import com.ryanrvldo.data.network.response.commons.CreditsResponse
import com.ryanrvldo.data.network.response.commons.GenreResponse
import com.ryanrvldo.data.network.response.commons.ImagesResponse
import com.ryanrvldo.data.network.response.commons.VideoResponse

data class MovieDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("vote_count") val ratingVotes: Int,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("runtime") val duration: Int?,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("images") val images: ImagesResponse,
    @SerializedName("videos") val videos: ListResponse<VideoResponse>,
    @SerializedName("credits") val credits: CreditsResponse
)

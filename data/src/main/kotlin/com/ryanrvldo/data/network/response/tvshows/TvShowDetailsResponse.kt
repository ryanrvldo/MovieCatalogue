package com.ryanrvldo.data.network.response.tvshows

import com.google.gson.annotations.SerializedName
import com.ryanrvldo.data.network.response.ListResponse
import com.ryanrvldo.data.network.response.commons.CreditsResponse
import com.ryanrvldo.data.network.response.commons.GenreResponse
import com.ryanrvldo.data.network.response.commons.ImagesResponse
import com.ryanrvldo.data.network.response.commons.VideoResponse

data class TvShowDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val releaseDate: String,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("vote_count") val ratingVotes: Int,
    @SerializedName("seasons") val seasons: List<SeasonResponse>,
    @SerializedName("content_ratings") val contentRatings: ListResponse<ContentRatingResponse>,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("images") val images: ImagesResponse,
    @SerializedName("videos") val videos: ListResponse<VideoResponse>,
    @SerializedName("credits") val credits: CreditsResponse,
)

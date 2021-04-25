package com.ryanrvldo.core.data.source.remote.response.tvshows

import com.google.gson.annotations.SerializedName
import com.ryanrvldo.core.data.source.remote.response.commons.*

data class TvShowResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("first_air_date") val releaseDate: String?,
    @SerializedName("vote_average") val rating: Double?,
    @SerializedName("vote_count") val ratingVotes: Int?,
    @SerializedName("backdrop_path") val backdrop: String?,
    @SerializedName("genres") val genreResponses: List<GenreResponse>?,
    @SerializedName("seasons") val seasons: List<SeasonResponse>?,
    @SerializedName("images") val images: ImagesResponse?,
    @SerializedName("content_ratings") val contentRatings: ListResponse<ContentRatingResponse>?,
    @SerializedName("videos") val videos: ListResponse<VideoResponse>?,
    @SerializedName("credits") val credits: CreditsResponse?,
    @SerializedName("similar") val similars: PagingResponse<SimilarResponse>?,
)

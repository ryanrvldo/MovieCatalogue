package com.ryanrvldo.data.network.response.movies

import com.google.gson.annotations.SerializedName
import com.ryanrvldo.data.network.response.ListResponse
import com.ryanrvldo.data.network.response.PagingResponse
import com.ryanrvldo.data.network.response.commons.*

data class MovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("vote_count") var ratingVotes: Int,
    @SerializedName("backdrop_path") val backdrop: String?,
    @SerializedName("runtime") var duration: Int?,
    @SerializedName("genres") var genres: List<GenreResponse>,
    @SerializedName("images") var images: ImagesResponse,
    @SerializedName("videos") var videos: ListResponse<VideoResponse>,
    @SerializedName("credits") var credits: CreditsResponse,
    @SerializedName("similar") var similars: PagingResponse<SimilarResponse>,
)

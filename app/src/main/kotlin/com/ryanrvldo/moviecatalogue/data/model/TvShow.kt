package com.ryanrvldo.moviecatalogue.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ryanrvldo.moviecatalogue.data.remote.response.*

@Entity(tableName = "favorite_tv")
data class TvShow(
    @Expose @SerializedName("id") @PrimaryKey val id: Int,
    @Expose @SerializedName("name") val title: String,
    @Expose val overview: String,
    @Expose @SerializedName("poster_path") @ColumnInfo(name = "poster_path") val posterPath: String,
    @Expose @SerializedName("first_air_date") @ColumnInfo(name = "release_date") val releaseDate: String,
    @Expose @SerializedName("vote_average") val rating: Float,
    @Expose @SerializedName("backdrop_path") val backdrop: String
) {
    @Ignore
    @Expose
    @SerializedName("vote_count")
    var ratingVotes: Int = 0

    @Ignore
    @Expose
    var genres: List<Genre>? = null

    @Ignore
    @Expose
    var seasons: List<Season>? = null

    @Ignore
    @Expose
    var images: ImageResponse? = null

    @Ignore
    @Expose
    @SerializedName("content_ratings")
    var contentRatingResponse: ContentRatingResponse? = null

    @Ignore
    @Expose
    var videos: VideosResponse? = null

    @Ignore
    @Expose
    var credits: CreditsResponse? = null

    @Ignore
    @Expose
    var similar: SimilarResponse? = null
}
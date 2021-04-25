package com.ryanrvldo.moviecatalogue.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ryanrvldo.moviecatalogue.data.remote.response.CreditsResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.ImageResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.SimilarResponse
import com.ryanrvldo.moviecatalogue.data.remote.response.VideosResponse

@Entity(tableName = "favorite_movie")
data class Movie(
    @Expose @SerializedName("id") @PrimaryKey val id: Int,
    @Expose val title: String,
    @Expose val overview: String,
    @Expose @SerializedName("poster_path") @ColumnInfo(name = "poster_path") val posterPath: String,
    @Expose @SerializedName("release_date") @ColumnInfo(name = "release_date") val releaseDate: String,
    @Expose @SerializedName("vote_average") val rating: Float,
    @Expose @SerializedName("backdrop_path") val backdrop: String
) {
    @Ignore
    @Expose
    @SerializedName("vote_count")
    var ratingVotes: Int = 0

    @Ignore
    @Expose
    @SerializedName("runtime")
    var duration: Int = 0

    @Ignore
    @Expose
    @SerializedName("genres")
    var genres: List<Genre> = emptyList()

    @Ignore
    @Expose
    var images: ImageResponse? = null

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
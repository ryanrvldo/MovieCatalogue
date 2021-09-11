package com.ryanrvldo.domain.model.tvshows

import com.ryanrvldo.domain.model.commons.Cast
import com.ryanrvldo.domain.model.commons.Image
import com.ryanrvldo.domain.model.commons.Video

data class TvShowDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val rating: Double,
    val ratingVotes: Int,
    val seasons: List<Season>,
    val contentRatings: List<ContentRating>,
    val genres: List<String>,
    val backdropImages: List<Image>,
    val videos: List<Video>,
    val casts: List<Cast>,
)

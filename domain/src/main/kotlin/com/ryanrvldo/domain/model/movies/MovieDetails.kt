package com.ryanrvldo.domain.model.movies

import com.ryanrvldo.domain.model.commons.Cast
import com.ryanrvldo.domain.model.commons.Image
import com.ryanrvldo.domain.model.commons.Video

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val rating: Double,
    val ratingVotes: Int,
    val duration: Int?,
    val genres: List<String>,
    val backdropImages: List<Image>,
    val videos: List<Video>,
    val casts: List<Cast>
)

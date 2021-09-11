package com.ryanrvldo.domain.model.movies

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val rating: Double,
    var ratingVotes: Int,
)

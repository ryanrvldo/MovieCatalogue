package com.ryanrvldo.domain.model.tvshows

data class Season(
    val id: Int,
    val name: String,
    val airDate: String,
    val overview: String,
    val totalEpisodes: Int,
    val posterPath: String?,
    val number: Int,
)

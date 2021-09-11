package com.ryanrvldo.domain.model.commons

data class Video(
    val id: String,
    val key: String,
    val title: String,
    val site: String,
    val type: String,
    val isOfficial: Boolean,
    val publishedDate: String,
)
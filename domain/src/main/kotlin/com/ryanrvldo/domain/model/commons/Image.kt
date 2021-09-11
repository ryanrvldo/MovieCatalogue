package com.ryanrvldo.domain.model.commons

data class Image(
    val path: String,
    val height: Int,
    val width: Int,
    val rating: Double,
    val ratingCount: Int
)
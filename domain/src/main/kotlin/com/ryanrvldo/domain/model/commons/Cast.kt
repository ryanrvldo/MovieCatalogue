package com.ryanrvldo.domain.model.commons

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val gender: Int?,
    val profilePath: String?,
    val castId: Int,
    val creditId: String,
    val order: Int
)

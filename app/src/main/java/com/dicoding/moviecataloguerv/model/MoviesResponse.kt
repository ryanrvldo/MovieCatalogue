package com.dicoding.moviecataloguerv.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val  page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val totalPage: Int
)
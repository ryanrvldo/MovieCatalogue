package com.ryanrvldo.moviecatalogue.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Similar(
    @Expose val id: Int,
    @Expose val title: String,
    @Expose val name: String,
    @Expose @SerializedName("poster_path") val posterPath: String,
    @Expose @SerializedName("vote_average") val rating: Float
)
package com.ryanrvldo.moviecatalogue.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Episode(
    @Expose val id: Int,
    @SerializedName("air_date") @Expose val airDate: String,
    @SerializedName("episode_number") @Expose val number: Int,
    @Expose val name: String,
    @Expose val overview: String,
    @SerializedName("still_path") @Expose val stillPath: String
)
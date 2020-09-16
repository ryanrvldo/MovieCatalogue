package com.ryanrvldo.moviecatalogue.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContentRating(
    @SerializedName("iso_3166_1") @Expose val iso: String,
    @Expose val rating: String
)
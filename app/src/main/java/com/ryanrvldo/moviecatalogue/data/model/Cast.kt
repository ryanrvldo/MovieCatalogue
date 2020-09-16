package com.ryanrvldo.moviecatalogue.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cast(
    @Expose val id: Int,
    @Expose val name: String,
    @Expose val character: String,
    @Expose val gender: Int,
    @SerializedName("credit_id") @Expose val creditId: String,
    @SerializedName("profile_path") @Expose val profilePath: String
)
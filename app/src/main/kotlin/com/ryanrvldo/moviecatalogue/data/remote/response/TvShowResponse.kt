package com.ryanrvldo.moviecatalogue.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ryanrvldo.moviecatalogue.data.model.TvShow

data class TvShowResponse(
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("results") @Expose val tvShowItems: List<TvShow>,
    @SerializedName("total_pages") @Expose val totalPages: Int,
    @SerializedName("total_results") @Expose val totalResults: Int
)
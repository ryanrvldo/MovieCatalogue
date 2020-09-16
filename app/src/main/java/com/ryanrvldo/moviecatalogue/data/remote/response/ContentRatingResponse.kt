package com.ryanrvldo.moviecatalogue.data.remote.response

import com.google.gson.annotations.Expose
import com.ryanrvldo.moviecatalogue.data.model.ContentRating

data class ContentRatingResponse(
    @Expose val results: List<ContentRating>
)
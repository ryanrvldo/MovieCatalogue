package com.ryanrvldo.moviecatalogue.data.model

import com.google.gson.annotations.Expose

data class Genre(
    @Expose val id: Int,
    @Expose val name: String
)
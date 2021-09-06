package com.ryanrvldo.data.source.paging

import com.ryanrvldo.data.constants.Category
import com.ryanrvldo.data.network.response.movies.MovieResponse
import com.ryanrvldo.data.network.service.MovieService
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val service: MovieService
) : BasePagingSource<MovieResponse>() {

    private var _category: String = Category.POPULAR

    fun setCategory(category: String) {
        _category = category
    }

    override suspend fun load(nextPage: Int) = service.getByCategory(_category, nextPage)

}
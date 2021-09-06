package com.ryanrvldo.data.source.paging

import com.ryanrvldo.data.constants.Category
import com.ryanrvldo.data.network.response.tvshows.TvShowResponse
import com.ryanrvldo.data.network.service.TvShowService
import javax.inject.Inject

class TvPagingSource @Inject constructor(
    private val service: TvShowService
) : BasePagingSource<TvShowResponse>() {

    private var _category: String = Category.POPULAR

    fun setCategory(category: String) {
        _category = category
    }

    override suspend fun load(nextPage: Int) = service.getByCategory(_category, nextPage)

}
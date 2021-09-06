package com.ryanrvldo.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingData
import com.ryanrvldo.data.network.response.tvshows.TvShowResponse
import com.ryanrvldo.data.network.service.TvShowService
import com.ryanrvldo.data.source.paging.TvPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvRemoteDataSource @Inject constructor(
    private val service: TvShowService,
    private val tvPagingSource: TvPagingSource
) {

    fun getTvByCategory(category: String): Flow<PagingData<TvShowResponse>> {
        tvPagingSource.setCategory(category)
        val pager = Pager(tvPagingSource.pagingConfig) { tvPagingSource }
        return pager.flow
    }

    suspend fun getTvShowDetails(id: Int): TvShowResponse = service.getDetails(id)

}
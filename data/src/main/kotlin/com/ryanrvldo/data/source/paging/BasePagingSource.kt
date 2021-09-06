package com.ryanrvldo.data.source.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ryanrvldo.data.constants.Constants
import com.ryanrvldo.data.network.response.PagingResponse

abstract class BasePagingSource<V : Any> : PagingSource<Int, V>() {

    open val pagingConfig: PagingConfig
        get() = PagingConfig(
            pageSize = Constants.DEFAULT_PAGE_SIZE,
            initialLoadSize = Constants.DEFAULT_PAGE_SIZE * 2,
            maxSize = 60,
            prefetchDistance = 3
        )

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    protected abstract suspend fun load(nextPage: Int): PagingResponse<V>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> = try {
        val nextPage = params.key ?: 1
        val response = load(nextPage)
        LoadResult.Page(
            data = response.results,
            prevKey = if (nextPage == 1) null else nextPage - 1,
            nextKey = if (nextPage == response.totalPages) null else nextPage + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

}
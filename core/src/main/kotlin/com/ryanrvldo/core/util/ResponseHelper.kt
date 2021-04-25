package com.ryanrvldo.core.util

import com.ryanrvldo.core.data.source.remote.network.ApiResponse
import com.ryanrvldo.core.data.source.remote.response.commons.PagingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

object ResponseHelper {

    suspend fun <D> handlePagingResponse(invoke: suspend () -> PagingResponse<D>): Flow<ApiResponse<List<D>>> {
        return flow {
            val response = invoke()
            val results = response.results ?: emptyList()
            if (results.isNotEmpty()) {
                emit(ApiResponse.Success(results))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { ex ->
            emit(ApiResponse.Error("Network call has failed for a following reason: ${ex.message}"))
        }
    }

}

package com.ryanrvldo.data.util

import com.ryanrvldo.data.error.NetworkException
import com.ryanrvldo.data.network.ApiResponse
import com.ryanrvldo.data.network.response.ListResponse
import com.ryanrvldo.data.network.response.PagingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend inline fun <D> handleNetworkException(
    crossinline apiCall: suspend () -> D
): Flow<ApiResponse<D>> {
    return flow {
        try {
            val response = apiCall()
            emit(ApiResponse.Success(response))
        } catch (throwable: Throwable) {
            emit(ApiResponse.Error(NetworkException.asNetworkException(throwable)))
        }
    }
}

suspend inline fun <D> handleNetworkExceptionFromPagingResponse(
    crossinline apiCall: suspend () -> PagingResponse<D>
): Flow<ApiResponse<List<D>>> {
    return flow {
        try {
            val response = apiCall()
            emit(ApiResponse.Success(response.results))
        } catch (throwable: Throwable) {
            emit(ApiResponse.Error(NetworkException.asNetworkException(throwable)))
        }
    }
}

suspend inline fun <D> handleNetworkExceptionFromListResponse(
    crossinline apiCall: suspend () -> ListResponse<D>
): Flow<ApiResponse<List<D>>> {
    return flow {
        try {
            val response = apiCall()
            emit(ApiResponse.Success(response.results))
        } catch (throwable: Throwable) {
            emit(ApiResponse.Error(NetworkException.asNetworkException(throwable)))
        }
    }
}

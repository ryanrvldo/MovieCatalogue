package com.ryanrvldo.data.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: Throwable) : ApiResponse<Nothing>()
}

package com.ryanrvldo.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest

suspend fun <T> resultOf(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call.invoke())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun <T, R> Flow<T>.mapResultOf(mapper: (T) -> R): Flow<Result<R>> = mapLatest {
    Result.success(mapper(it))
}.catch { emit(Result.failure(it)) }
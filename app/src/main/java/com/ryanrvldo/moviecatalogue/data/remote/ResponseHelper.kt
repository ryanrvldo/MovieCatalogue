package com.ryanrvldo.moviecatalogue.data.remote

import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Response

abstract class ResponseHelper {

    protected suspend fun <T> getResponseResult(call: suspend () -> Response<T>): Resource<T> {
        EspressoIdlingResource.increment()
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return throwError("${response.code()}: ${response.message()}")
        } catch (e: Exception) {
            return throwError(e.message ?: "No internet connection")
        } finally {
            EspressoIdlingResource.decrement()
        }
    }

    private fun <T> throwError(message: String): Resource<T> =
        Resource.error("Network call has failed for a following reason: $message")

}
/*
 * Copyright 2022 Rian Rivaldo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ryanrvldo.movielibrary.core.network.error

import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import java.io.IOException
import java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import retrofit2.HttpException
import retrofit2.Response

/**
 * An exception occurred while fetch data from remote server
 * @param code The HTTP response code from server, default is [400]
 * @param url The request url
 * @param errorType The [Type] from error
 * @param message The error message from server
 * @param exception The throwable when call remote API
 */
@RestrictTo(LIBRARY)
internal class NetworkException(
    val code: Int?,
    val url: String?,
    val errorType: Type,
    message: String?,
    exception: Throwable,
) : RuntimeException(message, exception) {

    companion object {
        private fun httpError(
            response: Response<*>?,
            httpException: HttpException
        ) = NetworkException(
            code = response?.code(),
            url = response?.raw()?.request?.url.toString(),
            errorType = Type.HTTP,
            message = response?.errorBody()?.string(),
            exception = httpException
        )

        private fun networkError(exception: IOException) = NetworkException(
            code = HTTP_CLIENT_TIMEOUT,
            url = null,
            errorType = Type.NETWORK,
            message = exception.message,
            exception = exception
        )

        private fun unexpectedError(exception: Throwable) = NetworkException(
            code = HTTP_INTERNAL_ERROR,
            url = null,
            errorType = Type.UNEXPECTED,
            message = exception.message,
            exception = exception
        )

        /**
         * Create a new [NetworkException] from current throwable
         * @return new [NetworkException] from current throwable
         */
        fun Throwable.asNetworkException(): NetworkException {
            return when (this) {
                is NetworkException -> this
                // We had non-200 http error
                is HttpException -> httpError(this.response(), this)
                // A network error happened
                is IOException -> networkError(this)
                // We don't know what happened. We need to simply convert to an unknown error
                else -> unexpectedError(this)
            }
        }
    }

    override fun toString(): String {
        return "${super.toString()} : $errorType : $url : "
    }
}

package com.ryanrvldo.data.error

import com.ryanrvldo.data.BuildConfig
import com.ryanrvldo.data.error.NetworkException.Type
import com.ryanrvldo.data.network.response.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR

/**
 * An exception occurred while fetch data from remote server
 * @param code The HTTP response code from server, default is [400]
 * @param url The request url
 * @param errorType The [Type] from error
 * @param message The error message from server
 * @param exception The throwable when call remote API
 */
class NetworkException(
    val code: Int? = 400,
    val url: String?,
    val errorType: Type,
    message: String?,
    exception: Throwable,
) : RuntimeException(message, exception) {

    /**
     * Identifies the event kind which triggered a [NetworkException].
     */
    enum class Type {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,

        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,

        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    companion object {
        private fun httpError(
            response: Response<*>?,
            httpException: HttpException,
        ): NetworkException {
            val url = response?.raw()?.request?.url.toString()
            val code = response?.code()
            val message = response?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                val errorResponse = moshiAdapter.fromJson(it)
                errorResponse?.statusMessage
            }
            return NetworkException(code, url, Type.HTTP, message, httpException)
        }

        private fun networkError(exception: IOException): NetworkException {
            return NetworkException(
                code = HTTP_CLIENT_TIMEOUT,
                url = null,
                errorType = Type.NETWORK,
                message = exception.message,
                exception = exception
            )
        }

        private fun unexpectedError(exception: Throwable): NetworkException {
            return NetworkException(
                HTTP_INTERNAL_ERROR,
                url = null,
                errorType = Type.UNEXPECTED,
                message = exception.message,
                exception = exception
            )
        }

        /**
         * Create a new [NetworkException] from current throwable
         * @param throwable The throwable occurred when call remote api
         * @return new [NetworkException] from current throwable
         */
        fun asNetworkException(throwable: Throwable): NetworkException {
            if (BuildConfig.DEBUG) {
                Timber.e(throwable)
            }
            return when (throwable) {
                is NetworkException -> throwable
                is HttpException -> {
                    // We had non-200 http error
                    val response = throwable.response()
                    return httpError(response, throwable)
                }
                is IOException -> {
                    // A network error happened
                    networkError(throwable)
                }
                else -> {
                    // We don't know what happened. We need to simply convert to an unknown error
                    unexpectedError(throwable)
                }
            }
        }
    }

    override fun toString(): String {
        return super.toString() + " : " + errorType + " : " + url + " : "
    }

}
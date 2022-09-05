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

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.movielibrary.core.network.error.NetworkException.Companion.asNetworkException
import com.ryanrvldo.movielibrary.core.network.util.buildHttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import org.junit.Test

/* ktlint-disable max-line-length */

class NetworkExceptionTest {

    @Test
    fun `test convert HttpException to network exception`() {
        val httpException = buildHttpException(HttpURLConnection.HTTP_NOT_FOUND)
        val actual = httpException.asNetworkException()

        assertThat(actual.code).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
        assertThat(actual.url).isEqualTo(httpException.response()?.raw()?.request?.url.toString())
        assertThat(actual.errorType).isEqualTo(Type.HTTP)
        assertThat(actual).hasCauseThat().isEqualTo(httpException)
    }

    @Test
    fun `test convert IOException to network exception`() {
        val ioException = SocketTimeoutException("timeout")
        val actual = ioException.asNetworkException()

        assertThat(actual.code).isEqualTo(HttpURLConnection.HTTP_CLIENT_TIMEOUT)
        assertThat(actual.url).isNull()
        assertThat(actual.errorType).isEqualTo(Type.NETWORK)
        assertThat(actual.message).isEqualTo(ioException.message)
        assertThat(actual).hasCauseThat().isEqualTo(ioException)
    }

    @Test
    fun `test convert unexpected exception to network exception`() {
        val exception = Exception("Error")
        val actual = exception.asNetworkException()

        assertThat(actual.code).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
        assertThat(actual.url).isNull()
        assertThat(actual.errorType).isEqualTo(Type.UNEXPECTED)
        assertThat(actual.message).isEqualTo(exception.message)
        assertThat(actual).hasCauseThat().isEqualTo(exception)
    }

    @Test
    fun `test convert network exception`() {
        val causeException = Exception("Server Error")
        val networkException = NetworkException(
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            url = null,
            errorType = Type.UNEXPECTED,
            message = "Internal Server Error",
            exception = causeException
        )
        val actual = networkException.asNetworkException()

        assertThat(actual.code).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
        assertThat(actual.url).isNull()
        assertThat(actual.errorType).isEqualTo(Type.UNEXPECTED)
        assertThat(actual.message).isEqualTo(networkException.message)
        assertThat(actual).hasCauseThat().isEqualTo(causeException)
    }

    @Test
    fun `test network exception to string`() {
        val networkException = NetworkException(
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            url = null,
            errorType = Type.UNEXPECTED,
            message = "Internal Server Error",
            exception = Exception("Server Error")
        )
        val actual = networkException.toString()

        assertThat(actual).isEqualTo("${NetworkException::class.java.name}: Internal Server Error : ${Type.UNEXPECTED} : null : ")
    }
}

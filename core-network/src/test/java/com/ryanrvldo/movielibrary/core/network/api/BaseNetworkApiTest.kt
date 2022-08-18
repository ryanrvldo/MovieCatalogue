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

package com.ryanrvldo.movielibrary.core.network.api

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.movielibrary.core.network.BuildConfig
import com.ryanrvldo.movielibrary.core.network.config.AuthInterceptor
import com.ryanrvldo.movielibrary.core.network.di.TestNetworkConfigModule
import com.ryanrvldo.movielibrary.core.network.util.FakeResponse
import com.ryanrvldo.movielibrary.core.network.util.HttpMethod
import com.ryanrvldo.movielibrary.core.network.util.convertToObjectFromJson
import java.io.IOException
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit

/* ktlint-disable max-line-length */

/**
 * The base class for network api unit test.
 * All the service test class must be extends to this class and use several commons unit test.
 *
 */
internal abstract class BaseNetworkApiTest {

    protected lateinit var mockWebServer: MockWebServer

    protected lateinit var fakeRetrofit: Retrofit

    protected abstract suspend fun exampleServiceCall(): Any

    @Before
    open fun setUp() {
        mockWebServer = MockWebServer().apply {
            start(port = 8080)
        }
        fakeRetrofit = TestNetworkConfigModule.providesRetrofit(mockWebServer.url("/"))
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }

    /**
     * Test network api when end point is not exist then must be throws [HttpException] error
     * with the [HTTP_NOT_FOUND] response code and [FakeResponse.Error.NOT_FOUND] response body
     */
    @Test
    fun `given not found result from remote, should return network exception and handle error`() {
        // GIVEN
        enqueueResponse(HTTP_NOT_FOUND, FakeResponse.Error.NOT_FOUND)
        // WHEN
        val actualException = assertThrows(HttpException::class.java) {
            runTest { exampleServiceCall() }
        }
        // THEN
        assertThat(actualException.code()).isEqualTo(HTTP_NOT_FOUND)
        assertThat(actualException.message).isEqualTo("HTTP $HTTP_NOT_FOUND Client Error")
    }

    /**
     * Test network api when request not provides api key then must be throws [HttpException]
     * with the [HTTP_UNAUTHORIZED] response code and [FakeResponse.Error.UNAUTHORIZED] response body
     */
    @Test
    fun `given unauthorized request from remote, should return network exception and handle error`() {
        // GIVEN
        enqueueResponse(HTTP_UNAUTHORIZED, FakeResponse.Error.UNAUTHORIZED)
        // WHEN
        val actualException = assertThrows(HttpException::class.java) {
            runTest { exampleServiceCall() }
        }
        // THEN
        assertThat(actualException.code()).isEqualTo(HTTP_UNAUTHORIZED)
        assertThat(actualException.message).isEqualTo("HTTP $HTTP_UNAUTHORIZED Client Error")
    }

    /**
     * Test service when back-end is down or error then must be throws [HttpException]
     * with the [HTTP_INTERNAL_ERROR] response code
     */
    @Test
    fun `given server error when get data from remote, should return network exception and handle error`() {
        // GIVEN
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest) = MockResponse()
                .setResponseCode(200)
                .setBody(FakeResponse.Movies.NOW_PLAYING_PAGE_1)
                .throttleBody(1024, 5, TimeUnit.SECONDS)
        }
        // WHEN
        val actualException = assertThrows(IOException::class.java) {
            runTest { exampleServiceCall() }
        }
        // THEN
        assertThat(actualException.message!!.lowercase()).contains("timeout")
    }

    /**
     * Test the service request has correct method, path url, and authentication header
     *
     * @param method The requested method
     * @param url The path url of request
     */
    fun assertCorrectRequest(
        method: HttpMethod,
        url: String,
        serviceCall: suspend () -> Any,
    ) = runTest {
        // GIVEN
        enqueueResponse(responseBody = "{\"status\":\"OK\"}")
        // WHEN
        serviceCall.invoke()
        // THEN
        val actualRequest = mockWebServer.takeRequest()
        assertThat(actualRequest.method).isEqualTo(method.name)
        assertThat(actualRequest.requestUrl!!.queryParameter(AuthInterceptor.API_KEY_PARAM))
            .isEqualTo(BuildConfig.TMDB_API_KEY)
        assertThat(actualRequest.path!!.replace("%2C", ",")).isEqualTo(url)
    }

    /**
     * Simulated test result of service request to the network
     * and assert the actual response from the [MockWebServer]
     *
     * @param fakeResponse The string response to be expected in request
     * @param serviceCall The function of the service that call the network.
     */
    inline fun <reified R> assertSuccessResultResponse(
        fakeResponse: String,
        crossinline serviceCall: suspend () -> R,
        noinline assertions: (expected: R, actual: R) -> Unit
    ) = runTest {
        // GIVEN
        enqueueResponse(responseBody = fakeResponse)
        val expected: R = convertToObjectFromJson(fakeResponse)!!
        // WHEN
        val actual = serviceCall()
        // THEN
        assertions.invoke(expected, actual)
    }

    /**
     * Enqueue response to the [MockWebServer]
     *
     * @param code The response code of the request
     * @param responseBody The response body that expected
     * @param headers The header response that expected
     */
    protected fun enqueueResponse(
        code: Int = 200,
        responseBody: String,
        headers: Map<String, String>? = null
    ): MockResponse {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(code)
        headers?.let {
            for ((key, value) in it) {
                mockResponse.addHeader(key, value)
            }
        }
        mockWebServer.enqueue(mockResponse.setBody(responseBody))
        return mockResponse
    }
}

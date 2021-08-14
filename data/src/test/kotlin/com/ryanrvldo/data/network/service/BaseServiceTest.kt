package com.ryanrvldo.data.network.service

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.data.BuildConfig
import com.ryanrvldo.data.constants.HttpMethod
import com.ryanrvldo.data.di.NetworkInjection
import com.ryanrvldo.data.error.NetworkException
import com.ryanrvldo.data.network.response.FakeResponse
import com.ryanrvldo.data.util.convertToObjectFromJson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.jupiter.api.*
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.net.HttpURLConnection.*

/**
 * The base class for service network unit test.
 * All the service test class must be extends to this class and use several commons unit test.
 *
 */
internal abstract class BaseServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var fakeRetrofit: Retrofit

    @BeforeEach
    internal open fun setUp() {
        mockWebServer = MockWebServer().apply {
            start()
        }
        fakeRetrofit = NetworkInjection.provideRetrofit(mockWebServer.url("/"))
        mockWebServer.dispatcher = this.dispatcher
    }

    @AfterEach
    internal open fun tearDown() {
        mockWebServer.shutdown()
    }

    internal abstract suspend fun exampleServiceCall(): Any
    abstract val dispatcher: QueueDispatcher

    @Nested
    @DisplayName("Commons service test")
    inner class CommonTest {

        /**
         * Test service when end point is not exist then must be throws [NetworkException] error
         * with the [HttpURLConnection.HTTP_NOT_FOUND] response code and [FakeResponse.NOT_FOUND_ERROR] response body
         * Use this unit test to the each of service test, to make sure the service already handle the network error.
         * Just to be clear just call this method in one service.
         */
        @Test
        @DisplayName("given not found result from remote, should return network exception and handle error")
        fun givenNotFoundResultThenAssertResponse() {
            // GIVEN
            mockWebServer.dispatcher = QueueDispatcher()
            enqueueResponse(HTTP_NOT_FOUND, FakeResponse.NOT_FOUND_ERROR)

            // WHEN
            val actualException = assertThrows<HttpException> {
                runBlocking {
                    exampleServiceCall()
                }
            }

            // THEN
            assertThat(actualException.code()).isEqualTo(HTTP_NOT_FOUND)
            assertThat(actualException.message).isEqualTo("HTTP $HTTP_NOT_FOUND Client Error")
        }

        /**
         * Test service when request not provides api key then must be throws [NetworkException]
         * with the [HttpURLConnection.HTTP_UNAUTHORIZED] response code and [FakeResponse.UNAUTHORIZED_ERROR] response body
         * Use this unit test to the each of service test, to make sure the service already provide api key.
         * Just to be clear just call this method in one service.
         */
        @Test
        @DisplayName("given unauthorized request from remote, should return network exception and handle error")
        fun givenUnauthorizedErrorResultThenAssertResponse() {
            // GIVEN
            mockWebServer.dispatcher = QueueDispatcher()
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HTTP_UNAUTHORIZED)
                    .setBody(FakeResponse.UNAUTHORIZED_ERROR)
            )

            // WHEN
            val actualException = assertThrows<HttpException> {
                runBlocking {
                    exampleServiceCall()
                }
            }

            // THEN
            assertThat(actualException.code()).isEqualTo(HTTP_UNAUTHORIZED)
            assertThat(actualException.message).isEqualTo("HTTP $HTTP_UNAUTHORIZED Client Error")
        }

        /**
         * Test service when back-end is down or error then must be throws [NetworkException]
         * with the [HttpURLConnection.HTTP_INTERNAL_ERROR] response code
         * Use this unit test to the each of service test, to make sure the service already handle the network error.
         * Just to be clear just call this method in one service.
         */
        @Test
        @DisplayName("given server error or down when get data from remote, should return network exception and handle error")
        fun givenServerErrorResultThenAssertResponse() {
            // GIVEN
            mockWebServer.dispatcher = QueueDispatcher()
            mockWebServer.enqueue(MockResponse().setResponseCode(HTTP_INTERNAL_ERROR))

            // WHEN
            val actualException = assertThrows<HttpException> {
                runBlocking {
                    exampleServiceCall()
                }
            }

            // THEN
            assertThat(actualException.code()).isEqualTo(HTTP_INTERNAL_ERROR)
            assertThat(actualException.message).isEqualTo("HTTP $HTTP_INTERNAL_ERROR Server Error")
        }

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
        // Define mock response
        val mockResponse = MockResponse()
        // Set response code
        mockResponse.setResponseCode(code)
        // Set headers
        headers?.let {
            for ((key, value) in it) {
                mockResponse.addHeader(key, value)
            }
        }
        // Set body
        this.mockWebServer.enqueue(mockResponse.setBody(responseBody))
        return mockResponse
    }

    /**
     * Test the service request has correct method, path url, and authentication header
     *
     * @param method The requested method
     * @param url The path url of request
     */
    fun givenSuccessResultThenAssertCorrectRequest(
        method: HttpMethod,
        url: String,
        serviceCall: suspend () -> Any,
    ) = runBlocking {
        // GIVEN
        enqueueResponse(responseBody = "Test")

        // WHEN
        serviceCall.invoke()

        // THEN
        val actualRequest = mockWebServer.takeRequest()
        assertThat(actualRequest.method).isEqualTo(method.name)
        assertThat(actualRequest.requestUrl!!.queryParameter("api_key"))
            .isEqualTo(BuildConfig.TMDB_API_KEY)
        assertThat(actualRequest.path!!.replace("%2C", ",")).isEqualTo(url)

        val actualResponse = mockWebServer.dispatcher.peek()
        assertThat(actualResponse.status).contains("200 OK")
    }

    /**
     * Simulated test result of service request to the network and assert the actual response from the [MockWebServer]
     *
     * @param fakeResponse The string response to be expected in request
     * @param serviceCall The function of the service that call the network.
     */
    inline fun <reified R> givenSuccessResultThenAssertResponse(
        fakeResponse: String,
        crossinline serviceCall: suspend () -> R,
        noinline assertions: (expected: R, actual: R) -> Unit
    ) = runBlocking {
        // GIVEN
        enqueueResponse(responseBody = fakeResponse)
        val expected: R =
            convertToObjectFromJson(mockWebServer.dispatcher.peek().getBody()!!.readUtf8())!!

        // WHEN
        val actual = serviceCall()

        // THEN
        assertions.invoke(expected, actual)
    }

}
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

package com.ryanrvldo.movielibrary.core.network.util

import com.google.common.truth.Truth.assertThat
import com.ryanrvldo.movielibrary.core.network.error.NetworkException
import com.ryanrvldo.movielibrary.core.network.error.NetworkException.Companion.asNetworkException
import com.ryanrvldo.movielibrary.core.network.fake.FakeNetworkApi
import com.ryanrvldo.movielibrary.core.network.fake.FakeNetworkDataSource
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.net.SocketTimeoutException
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

/* ktlint-disable max-line-length */

class RetrofitExtensionsKtTest {

    private lateinit var subject: FakeNetworkDataSource

    private val mockApi: FakeNetworkApi = mockk()

    @Before
    fun setUp() {
        subject = FakeNetworkDataSource(mockApi)
    }

    @After
    fun tearDown() {
        clearMocks(mockApi)
    }

    @Test
    fun `given HttpException with 429 response code when call api, should retry call api 3 times and throws NetworkException`() {
        val httpException = buildHttpException(429, "Retry-After" to 3600L.toString())
        coEvery { mockApi.getFakeData() } throws httpException
        val expected = httpException.asNetworkException()

        val actual = assertThrows(NetworkException::class.java) {
            runTest { subject.getFakeData() }
        }

        assertThat(actual.code).isEqualTo(expected.code)
        assertThat(actual.url).isEqualTo(expected.url)
        assertThat(actual.errorType).isEqualTo(expected.errorType)
        assertThat(actual).hasCauseThat().isEqualTo(httpException)

        coVerify(exactly = 3) { mockApi.getFakeData() }
    }

    @Test
    fun `given HttpException with non-429 response code when call api, should not retry call api and throws NetworkException`() {
        val exception = buildHttpException(500)
        coEvery { mockApi.getFakeData() } throws exception
        val expected = exception.asNetworkException()

        val actual = assertThrows(NetworkException::class.java) {
            runTest { subject.getFakeData() }
        }

        assertThat(actual.code).isEqualTo(expected.code)
        assertThat(actual.url).isEqualTo(expected.url)
        assertThat(actual.errorType).isEqualTo(expected.errorType)
        assertThat(actual).hasCauseThat().isEqualTo(exception)

        coVerify(exactly = 1) { mockApi.getFakeData() }
    }

    @Test
    fun `given IOException when call api, should retry call api 3 times and throws NetworkException`() {
        val ioException = SocketTimeoutException("timeout")
        coEvery { mockApi.getFakeData() } throws ioException
        val expected = ioException.asNetworkException()

        val actual = assertThrows(NetworkException::class.java) {
            runTest { subject.getFakeData() }
        }

        assertThat(actual.code).isEqualTo(expected.code)
        assertThat(actual.url).isEqualTo(expected.url)
        assertThat(actual.errorType).isEqualTo(expected.errorType)
        assertThat(actual.message).isEqualTo(expected.message)
        assertThat(actual).hasCauseThat().isEqualTo(ioException)

        coVerify(exactly = 3) { mockApi.getFakeData() }
    }

    @Test
    fun `given unexpected exception when call api, should not retry call api and throws NetworkException`() {
        val exception = Exception("ERROR")
        coEvery { mockApi.getFakeData() } throws exception
        val expected = exception.asNetworkException()

        val actual = assertThrows(NetworkException::class.java) {
            runTest { subject.getFakeData() }
        }

        assertThat(actual.code).isEqualTo(expected.code)
        assertThat(actual.url).isEqualTo(expected.url)
        assertThat(actual.errorType).isEqualTo(expected.errorType)
        assertThat(actual.message).isEqualTo(expected.message)
        assertThat(actual).hasCauseThat().isEqualTo(exception)

        coVerify(exactly = 1) { mockApi.getFakeData() }
    }

    @Test
    fun `given HttpException with 429 response code, should return true`() {
        val subject = buildHttpException(429)
        val actual = defaultShouldRetry(subject)
        assertThat(actual).isTrue()
    }

    @Test
    fun `given HttpException with not 429 response code, should return false`() {
        val subject = buildHttpException(500)
        val actual = defaultShouldRetry(subject)
        assertThat(actual).isFalse()
    }

    @Test
    fun `given timeout exception, should return true`() {
        val subject = SocketTimeoutException("timeout")
        val actual = defaultShouldRetry(subject)
        assertThat(actual).isTrue()
    }

    @Test
    fun `given unexpected exception, should return false`() {
        val subject = Exception("Error")
        val actual = defaultShouldRetry(subject)
        assertThat(actual).isFalse()
    }

    @Test
    fun `given HttpException with retry-after response header, should return correct retryAfter value`() {
        val expected = 3600L
        val subject = buildHttpException(429, "Retry-After" to expected.toString())
        val actual = subject.retryAfter
        assertThat(actual).isNotNull()
        assertThat(actual).isEqualTo(expected + 10)
    }

    @Test
    fun `given HttpException with no retry-after response header, should return null retryAfter value`() {
        val subject = buildHttpException(429)
        val actual = subject.retryAfter
        assertThat(actual).isNull()
    }

    @Test
    fun `given HttpException with unexpected retry-after response head, should return null`() {
        val subject = buildHttpException(429, "Retry-After" to "Unexpected")
        assertThat(subject.retryAfter).isNull()
    }
}

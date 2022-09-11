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
import com.ryanrvldo.movielibrary.core.network.model.MovieResponse
import com.ryanrvldo.movielibrary.core.network.model.PagingResponse
import com.ryanrvldo.movielibrary.core.network.util.withRetry
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import kotlinx.coroutines.test.runTest
import okhttp3.internal.EMPTY_RESPONSE
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

/* ktlint-disable max-line-length */

internal class TmdbMoviesNetworkDataSourceTest {

    private lateinit var subject: TmdbMoviesNetworkDataSource

    private val mockApi: TmdbMoviesNetworkApi = mockk()

    @Before
    fun setUp() {
        subject = TmdbMoviesNetworkDataSource(mockApi)
    }

    @After
    fun tearDown() {
        clearMocks(mockApi)
    }

    @Test
    fun `when get now playing movies, should call network api`() = runTest {
        val expected = PagingResponse<MovieResponse>(1, emptyList(), 1, 0)
        coEvery { mockApi.getNowPlayingMovies() } returns expected

        val actual = subject.getNowPlayingMovies()

        assertThat(actual).isEqualTo(expected)
        coVerify(exactly = 1) { withRetry { mockApi.getNowPlayingMovies() } }
        coVerify(exactly = 1) { mockApi.getNowPlayingMovies() }
    }

    @Test
    fun `given error result from network when get now playing movies, should throws exception`() {
        val expected = HttpException(Response.error<Any>(HTTP_NOT_FOUND, EMPTY_RESPONSE))
        coEvery { mockApi.getNowPlayingMovies(any()) } throws expected

        val actual = assertThrows(HttpException::class.java) {
            runTest { mockApi.getNowPlayingMovies() }
        }

        assertThat(actual).hasMessageThat().contains(expected.message())
        coVerify(exactly = 1) { withRetry { mockApi.getNowPlayingMovies(any()) } }
        coVerify(exactly = 1) { mockApi.getNowPlayingMovies(any()) }
    }
}

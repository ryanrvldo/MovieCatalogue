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
import com.ryanrvldo.movielibrary.core.network.util.FakeResponse
import com.ryanrvldo.movielibrary.core.network.util.HttpMethod
import org.junit.Before
import org.junit.Test
import retrofit2.create

/* ktlint-disable max-line-length */

internal class TmdbMoviesNetworkApiTest : BaseNetworkApiTest() {

    private lateinit var subject: TmdbMoviesNetworkApi

    @Before
    override fun setUp() {
        super.setUp()
        subject = fakeRetrofit.create()
    }

    override suspend fun exampleServiceCall() = subject.getNowPlayingMovies()

    @Test
    internal fun `given success result when get now playing movies page 1, should have correct url and response code`() {
        assertCorrectRequest(
            HttpMethod.GET,
            "/movie/now_playing?page=1&api_key=${BuildConfig.TMDB_API_KEY}"
        ) {
            subject.getNowPlayingMovies(1)
        }
    }

    @Test
    internal fun `given success result when get now playing movies page 2, should have correct url and response code`() {
        assertCorrectRequest(
            HttpMethod.GET,
            "/movie/now_playing?page=2&api_key=${BuildConfig.TMDB_API_KEY}"
        ) {
            subject.getNowPlayingMovies(2)
        }
    }

    @Test
    internal fun `given success result when get now playing movies page 1, should have correct response`() {
        assertSuccessResultResponse(
            FakeResponse.Movies.NOW_PLAYING_PAGE_1,
            { subject.getNowPlayingMovies(1) }
        ) { expected, actual ->
            assertThat(actual.page).isEqualTo(expected.page)
            assertThat(actual.totalPages).isEqualTo(expected.totalPages)
            assertThat(actual.totalResults).isEqualTo(expected.totalResults)
            assertThat(actual.results).containsExactlyElementsIn(expected.results)
        }
    }

    @Test
    internal fun `given success result when get now playing movies page 2, should have correct response`() {
        assertSuccessResultResponse(
            FakeResponse.Movies.NOW_PLAYING_PAGE_2,
            { subject.getNowPlayingMovies(2) }
        ) { expected, actual ->
            assertThat(actual.page).isEqualTo(expected.page)
            assertThat(actual.totalPages).isEqualTo(expected.totalPages)
            assertThat(actual.totalResults).isEqualTo(expected.totalResults)
            assertThat(actual.results).containsExactlyElementsIn(expected.results)
        }
    }
}

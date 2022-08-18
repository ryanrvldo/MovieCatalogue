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

import com.ryanrvldo.movielibrary.core.network.MoviesNetworkDataSource
import com.ryanrvldo.movielibrary.core.network.model.MovieResponse
import com.ryanrvldo.movielibrary.core.network.model.PagingResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbMoviesNetworkDataSource @Inject constructor(
    private val moviesNetworkApi: TmdbMoviesNetworkApi
) : MoviesNetworkDataSource {
    override suspend fun getNowPlayingMovies(page: Int): PagingResponse<MovieResponse> {
        return moviesNetworkApi.getNowPlayingMovies(page)
    }
}

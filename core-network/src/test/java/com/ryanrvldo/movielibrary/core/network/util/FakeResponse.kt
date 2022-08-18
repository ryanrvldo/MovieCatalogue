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

sealed class FakeResponse {
    object Error {
        val NOT_FOUND by lazy {
            getResourceAsText("api-response/response_not_found.json")
        }
        val UNAUTHORIZED by lazy {
            getResourceAsText("api-response/response_unauthorized.json")
        }
    }

    object Movies {
        val NOW_PLAYING_PAGE_1 by lazy {
            getResourceAsText("api-response/response_movies_now_playing_1.json")
        }
        val NOW_PLAYING_PAGE_2 by lazy {
            getResourceAsText("api-response/response_movies_now_playing_2.json")
        }
    }
}

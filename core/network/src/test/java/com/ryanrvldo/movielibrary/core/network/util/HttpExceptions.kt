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

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

internal fun buildHttpException(
    code: Int,
    vararg headers: Pair<String, String>,
    body: ResponseBody = FakeResponse.Error.NOT_FOUND
        .toResponseBody("application/json".toMediaTypeOrNull())
): HttpException {
    val rawResponseBuilder = okhttp3.Response.Builder()
        .code(code)
        .request(Request.Builder().url("https://example.com").build())
        .protocol(Protocol.HTTP_1_1)
        .message("Error")
    headers.forEach {
        rawResponseBuilder.header(it.first, it.second)
    }
    return HttpException(Response.error<Any>(body, rawResponseBuilder.build()))
}

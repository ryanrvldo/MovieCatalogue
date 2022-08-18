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

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import java.io.IOException
import okio.buffer
import okio.source

/**
 * Use this method to get json files as string from resources folder to use in tests.
 */
fun getResourceAsText(path: String): String {
    val inputStream = object {}.javaClass.classLoader!!.getResourceAsStream(path)
        ?: throw IOException("$path is invalid.")
    val bufferSource = inputStream.source().buffer()
    return bufferSource.readUtf8()
}

inline fun <reified T> Gson.fromJsonWithType(json: String): T? = fromJson<T>(
    json, object : TypeToken<T>() {}.type
)

/**
 *
 * Convert from json to item with type T
 *
 * * This function returns for some items as [LinkedTreeMap]
 */
inline fun <reified T> convertToObjectFromJson(json: String): T? = Gson().fromJsonWithType<T>(json)

/**
 *
 * Convert from json to [List] of items with type T
 *
 * * This function returns for some items as [LinkedTreeMap]
 */
inline fun <reified T> fromJsonToListOf(json: String): List<T> {
    return GsonBuilder().create().fromJson(json, Array<T>::class.java).asList()
}

fun <T> Gson.mapFromLinkedTreeMap(map: Map<String, Any?>?, type: Class<T>): T? {
    if (map == null) return null

    val json = toJson(map)
    return fromJson(json, type)
}

inline fun <reified T : Any> convertFromJsonToListOf(json: String): List<T>? {

    val gson = GsonBuilder().create()

    val itemList = fromJsonToListOf<T>(json)

    if (itemList.first() !is LinkedTreeMap<*, *>)
        return itemList

    // Must use map here because the result is a list of LinkedTreeMaps
    val list: ArrayList<Map<String, Any?>>? = gson.fromJsonWithType(json)
    // handle type erasure

    return list?.mapNotNull {
        gson.mapFromLinkedTreeMap(it, T::class.java)
    }
}

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

package com.ryanrvldo.movielibrary.initializers

import com.ryanrvldo.movielibrary.BuildConfig
import com.ryanrvldo.movielibrary.core.initializer.AppInitializer
import com.ryanrvldo.movielibrary.core.logging.Logger
import javax.inject.Inject

class LoggerInitializer @Inject constructor(
    private val logger: Logger
) : AppInitializer {
    override fun init() = logger.setup(BuildConfig.DEBUG)
}

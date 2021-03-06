/*
 * Copyright 2021 The Android Open Source Project
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
package com.example.androiddevchallenge.timer.ext

import java.time.Duration
import kotlin.math.abs

fun Duration.formatDuration(): String {
    val seconds: Long = this.seconds
    val absSeconds = abs(seconds)
    val positive = String.format(
        "%02d:%02d",
        absSeconds % 3600 / 60,
        absSeconds % 60
    )
    return if (seconds < 0) "-$positive" else positive
}

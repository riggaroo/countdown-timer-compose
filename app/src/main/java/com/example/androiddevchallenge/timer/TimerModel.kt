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
package com.example.androiddevchallenge.timer

import java.time.Duration

data class TimerModel(
    val timerDuration: Duration = Duration.ofSeconds(10),
    val durationRemaining: Duration = timerDuration,
    val timerViewState: TimerViewState = TimerViewState.IDLE
) {
    fun isTimerRunning(): Boolean {
        return timerViewState == TimerViewState.RUNNING
    }

    fun getPercentageComplete(): Float {
        return (durationRemaining.toMillis().toFloat() / timerDuration.toMillis().toFloat())
    }
}

enum class TimerViewState {
    IDLE,
    RUNNING,
    FINISHED
}

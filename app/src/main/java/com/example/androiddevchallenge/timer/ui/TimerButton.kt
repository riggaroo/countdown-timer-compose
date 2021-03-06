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
package com.example.androiddevchallenge.timer.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.timer.TimerViewState

@Composable
fun TimerButton(timerViewState: TimerViewState, onButtonClick: () -> Unit) {
    Button(
        elevation = null,
        modifier = Modifier
            .padding(16.dp)
            .height(56.dp)
            .fillMaxWidth(),
        onClick = {
            onButtonClick()
        }
    ) {
        val text = when (timerViewState) {
            TimerViewState.IDLE -> {
                stringResource(id = R.string.start_timer)
            }
            TimerViewState.RUNNING -> {
                stringResource(id = R.string.stop_timer)
            }
            TimerViewState.FINISHED -> {
                stringResource(id = R.string.restart_timer)
            }
        }
        Text(text = text)
    }
}

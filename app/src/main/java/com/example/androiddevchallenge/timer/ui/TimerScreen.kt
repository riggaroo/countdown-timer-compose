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

import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.timer.ext.formatDuration
import com.example.androiddevchallenge.timer.ui.BackgroundGradient
import com.example.androiddevchallenge.timer.ui.DeterminateProgressBar
import com.example.androiddevchallenge.timer.ui.FlashingTimerText
import com.example.androiddevchallenge.timer.ui.TimerButton
import com.example.androiddevchallenge.timer.ui.TimerSetDuration
import com.example.androiddevchallenge.timer.ui.TimerText
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.Duration

@Composable
fun TimerScreen(timerViewModel: TimerViewModel, navController: NavController) {
    val viewStateObserver = timerViewModel.viewState.observeAsState(initial = TimerModel())
    val state = viewStateObserver.value
    val context = LocalContext.current

    LaunchedEffect(key1 = navController) {
        var mediaPlayer : MediaPlayer? = null
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        timerViewModel.viewEffectChannel.receiveAsFlow().collect { viewEffect ->
            when (viewEffect) {
                TimerViewEffect.TimerFinished -> {
                    startVibrating(vibrator = vibrator)
                    mediaPlayer = MediaPlayer.create(context, R.raw.alarm_2)
                    playSound(mediaPlayer!!)
                }
                TimerViewEffect.TimerReset -> {
                    stopVibrating(vibrator = vibrator)
                    stopPlayingSound(mediaPlayer = mediaPlayer)
                }
            }
        }
    }
    TimerContents(
        state = state,
        onTimerButtonPress = { timerViewModel.timerButtonPressed() },
        onAddTime = { duration ->
            timerViewModel.addTime(duration)
        },
        onRemoveTime = { duration ->
            timerViewModel.removeTime(duration)
        }
    )
}

@Preview
@Composable
fun PreviewTimerScreen() {
    TimerContents(
        state = TimerModel(), onTimerButtonPress = { },
        onAddTime = {}, onRemoveTime = {}
    )
}

@Composable
fun TimerContents(
    state: TimerModel,
    onTimerButtonPress: () -> Unit,
    onAddTime: (Duration) -> Unit,
    onRemoveTime: (Duration) -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        BackgroundGradient()
        ProvideWindowInsets {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Timer(state = state, onAddTime, onRemoveTime)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Bottom
            ) {
                TimerButton(state.timerViewState, onButtonClick = { onTimerButtonPress() })
            }
        }
    }
}

@Composable
fun Timer(
    state: TimerModel,
    onAddTime: (Duration) -> Unit,
    onRemoveTime: (Duration) -> Unit
) {
    when (state.timerViewState) {
        TimerViewState.RUNNING -> {
            val configuration = LocalConfiguration.current
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    TimerText(timerText = state.durationRemaining.formatDuration())
                }
                else -> {
                    DeterminateProgressBar(progress = state.getPercentageComplete()) {
                        TimerText(timerText = state.durationRemaining.formatDuration())
                    }
                }
            }
        }
        TimerViewState.IDLE -> {
            TimerSetDuration(
                timerDuration = state.timerDuration,
                onAddTime = {
                    onAddTime(it)
                },
                onRemoveTime = {
                    onRemoveTime(it)
                }
            )
        }
        TimerViewState.FINISHED -> {
            FlashingTimerText(timerText = "00:00")
        }
    }
}

fun stopVibrating(vibrator: Vibrator) {
    vibrator.cancel()
}

fun startVibrating(vibrator: Vibrator) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val vibratePattern = longArrayOf(0, 400, 800, 600, 800, 800, 800, 1000)
        val amplitudes = intArrayOf(0, 255, 0, 255, 0, 255, 0, 255)
        vibrator.vibrate(
            VibrationEffect.createWaveform(
                vibratePattern,
                amplitudes,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        // deprecated in API 26
        @Suppress("DEPRECATION")
        vibrator.vibrate(500)
    }
}

fun playSound(mediaPlayer: MediaPlayer) {
    mediaPlayer.setOnCompletionListener {
        mediaPlayer.release()
    }
    mediaPlayer.start()
}

fun stopPlayingSound(mediaPlayer: MediaPlayer?) {
    if (mediaPlayer?.isPlaying == true) {
        mediaPlayer.stop()
    }
}

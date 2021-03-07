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

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.time.Duration

class TimerViewModel : ViewModel() {

    private val _viewState = MutableLiveData<TimerModel>()
    val viewState: LiveData<TimerModel>
        get() = _viewState

    val viewEffectChannel = Channel<TimerViewEffect>()

    private var timer: CountDownTimer? = null

    init {
        _viewState.value = TimerModel()
    }

    private fun getCurrentState(): TimerModel {
        return _viewState.value!!
    }

    private fun startTimer(duration: Duration) {
        timer = object : CountDownTimer(duration.toMillis(), 10) {
            override fun onTick(millisUntilFinished: Long) {
                val currentState = getCurrentState()

                _viewState.value = currentState.copy(
                    timerViewState = TimerViewState.RUNNING,
                    durationRemaining = Duration.ofMillis(millisUntilFinished)
                )
            }

            override fun onFinish() {
                val currentState = getCurrentState()

                _viewState.value = currentState.copy(
                    timerViewState = TimerViewState.FINISHED,
                    durationRemaining = Duration.ZERO
                )
                viewModelScope.launch {
                    viewEffectChannel.send(TimerViewEffect.TimerFinished)
                }
            }
        }
        timer?.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        val currentState = getCurrentState()
        _viewState.value = currentState.copy(
            timerViewState = TimerViewState.IDLE,
            durationRemaining = currentState.timerDuration
        )
    }

    fun timerButtonPressed() {
        val currentState = getCurrentState()
        when (currentState.timerViewState) {
            TimerViewState.IDLE -> {
                startTimer(currentState.timerDuration)
            }
            TimerViewState.RUNNING -> {
                stopTimer()
            }
            TimerViewState.FINISHED -> {
                resetTimer()
            }
        }
    }

    fun addTime(duration: Duration) {
        val currentState = getCurrentState()

        _viewState.value = currentState.copy(timerDuration = currentState.timerDuration.plus(duration))
    }

    fun removeTime(duration: Duration) {
        val currentState = getCurrentState()

        val currentDuration = currentState.timerDuration
        if (currentDuration.minus(duration).isNegative) {
            return
        }
        _viewState.value = currentState.copy(timerDuration = currentDuration.minus(duration))
    }

    private fun resetTimer() {
        val currentState = getCurrentState()
        _viewState.value = currentState.copy(
            timerViewState = TimerViewState.IDLE,
            durationRemaining = currentState.timerDuration
        )
        viewModelScope.launch {
            viewEffectChannel.send(TimerViewEffect.TimerReset)
        }
    }
}

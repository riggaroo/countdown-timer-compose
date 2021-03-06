package com.example.androiddevchallenge.timer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Duration

class CountdownTimerViewModel : ViewModel() {

    private val _viewState = MutableLiveData<TimerModel>()
    val viewState: LiveData<TimerModel>
        get() = _viewState

    private var timer : CountDownTimer? = null

    init {
        _viewState.value = TimerModel()
    }

    private fun startTimer(duration: Duration) {
        timer = object : CountDownTimer(duration.toMillis(), Duration.ofSeconds(1).toMillis()) {
            override fun onTick(millisUntilFinished: Long) {
                _viewState.value = viewState.value!!.copy(
                    isRunning = true,
                    durationRemaining = Duration.ofMillis(millisUntilFinished)
                )
            }

            override fun onFinish() {
                _viewState.value = viewState.value!!.copy(isRunning = false, durationRemaining = Duration.ZERO)
            }
        }
        timer?.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        _viewState.value = viewState.value!!.copy(isRunning = false, durationRemaining = viewState.value!!.timerDuration)
    }

    fun timerButtonPressed() {
        if (viewState.value!!.isRunning) {
            stopTimer()
        } else {
            startTimer(viewState.value!!.timerDuration)
        }
    }
}
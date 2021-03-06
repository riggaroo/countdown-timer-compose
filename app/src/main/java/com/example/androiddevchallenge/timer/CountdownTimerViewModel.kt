package com.example.androiddevchallenge.timer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.time.Duration

class CountdownTimerViewModel : ViewModel() {

    private val _viewState = MutableLiveData<TimerModel>()
    val viewState: LiveData<TimerModel>
        get() = _viewState

    val viewEffectChannel = Channel<TimerViewEffect>()

    private var timer : CountDownTimer? = null

    init {
        _viewState.value = TimerModel()
    }

    private fun startTimer(duration: Duration) {
        timer = object : CountDownTimer(duration.toMillis(), Duration.ofSeconds(1).toMillis()) {
            override fun onTick(millisUntilFinished: Long) {
                _viewState.value = viewState.value!!.copy(
                    timerViewState = TimerViewState.RUNNING,
                    durationRemaining = Duration.ofMillis(millisUntilFinished)
                )
            }

            override fun onFinish() {
                _viewState.value = viewState.value!!.copy(
                    timerViewState = TimerViewState.FINISHED,
                    durationRemaining = Duration.ZERO)
                viewModelScope.launch {
                    viewEffectChannel.send(TimerViewEffect.TimerFinished)
                }
            }
        }
        timer?.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        _viewState.value = viewState.value!!.copy(
            timerViewState = TimerViewState.IDLE,
            durationRemaining = viewState.value!!.timerDuration)
    }

    fun timerButtonPressed() {
        val state = viewState.value!!
        when (state.timerViewState){
            TimerViewState.IDLE -> {
                startTimer(state.timerDuration)
            }
            TimerViewState.RUNNING -> {
                stopTimer()
            }
            TimerViewState.FINISHED -> {
                resetTimer()
            }
        }
    }

    private fun resetTimer() {
        _viewState.value = viewState.value!!.copy(
            timerViewState = TimerViewState.IDLE,
            durationRemaining = viewState.value!!.timerDuration)
        viewModelScope.launch {
            viewEffectChannel.send(TimerViewEffect.TimerReset)
        }
    }
}
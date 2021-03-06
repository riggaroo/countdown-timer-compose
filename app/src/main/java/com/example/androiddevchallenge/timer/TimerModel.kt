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

    fun getPercentageComplete() : Float {
        return 1 - (durationRemaining.toMillis().toFloat() / timerDuration.toMillis().toFloat())
    }
}


enum class TimerViewState {
    IDLE,
    RUNNING,
    FINISHED
}
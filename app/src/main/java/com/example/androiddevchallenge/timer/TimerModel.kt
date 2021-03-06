package com.example.androiddevchallenge.timer

import java.time.Duration

data class TimerModel(
    val timerDuration: Duration = Duration.ofMinutes(1),
    val durationRemaining: Duration = timerDuration,
    val isRunning: Boolean = false
)
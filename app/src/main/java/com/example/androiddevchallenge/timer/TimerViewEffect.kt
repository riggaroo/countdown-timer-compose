package com.example.androiddevchallenge.timer

sealed class TimerViewEffect {
    object TimerFinished: TimerViewEffect()
    object TimerReset: TimerViewEffect()
}
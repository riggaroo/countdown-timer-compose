package com.example.androiddevchallenge.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.BackgroundGradient
import com.example.androiddevchallenge.timer.ext.formatDuration

@Composable
fun TimerScreen(timerViewModel: CountdownTimerViewModel) {
    val viewStateObserver = timerViewModel.viewState.observeAsState(initial = TimerModel())
    val state = viewStateObserver.value
    Surface(color = MaterialTheme.colors.background) {
        BackgroundGradient()
        Column(modifier = Modifier.padding(16.dp)) {
            if (state.isRunning) {
                TimerText(timerText = state.durationRemaining.formatDuration())
            } else {
                TimerText(timerText = state.timerDuration.formatDuration())
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ){
            TimerButton(isTimerRunning = state.isRunning, onButtonClick = { timerViewModel.timerButtonPressed() })
        }
    }
}

@Composable
fun TimerText(timerText: String){
    Text(modifier = Modifier.fillMaxWidth().padding(16.dp),
        style = MaterialTheme.typography.h2,
        text = timerText,
        textAlign = TextAlign.Center
    )
}

@Composable
fun TimerButton(isTimerRunning: Boolean, onButtonClick: () -> Unit){
    Button(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        onClick = {
            onButtonClick()
        }) {
        val text = if (isTimerRunning){
            "Stop Timer"
        } else {
            "Start Timer"
        }
        Text(text = text)
    }
}
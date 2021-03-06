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
fun TimerButton(timerViewState: TimerViewState, onButtonClick: () -> Unit){
    Button(
        elevation = null,
        modifier = Modifier
            .padding(16.dp)
            .height(56.dp)
            .fillMaxWidth(),
        onClick = {
            onButtonClick()
        }) {
        val text = when (timerViewState){
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
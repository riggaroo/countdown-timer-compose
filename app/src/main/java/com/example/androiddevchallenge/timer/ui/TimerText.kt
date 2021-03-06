package com.example.androiddevchallenge.timer.ui

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FlashingTimerText(timerText: String){
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    TimerText(modifier = Modifier.alpha(alpha),
        timerText = timerText)
}

@Composable
fun TimerText(modifier: Modifier = Modifier, timerText: String) {
    Text(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        style = MaterialTheme.typography.h1,
        text = timerText,
        textAlign = TextAlign.Center
    )
}
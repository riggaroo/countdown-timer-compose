package com.example.androiddevchallenge.timer.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.rotate
import com.example.androiddevchallenge.ui.theme.lightPurple
import com.example.androiddevchallenge.ui.theme.orange
import com.example.androiddevchallenge.ui.theme.peach

@Composable
fun BackgroundGradient(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val colorFirst by infiniteTransition.animateColor(
        initialValue = orange,
        targetValue = peach,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val colorSecond by infiniteTransition.animateColor(
        initialValue = peach,
        targetValue = lightPurple,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val colorThird by infiniteTransition.animateColor(
        initialValue = lightPurple,
        targetValue = orange,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val brushBackground = Brush.verticalGradient(
            listOf(colorFirst, colorSecond, colorThird), 0f, size.height.toDp().toPx(), TileMode.Mirror
        )
        drawRect(brushBackground)
    }
}
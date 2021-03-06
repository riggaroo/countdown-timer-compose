package com.example.androiddevchallenge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import com.example.androiddevchallenge.ui.theme.lightPurple
import com.example.androiddevchallenge.ui.theme.orange
import com.example.androiddevchallenge.ui.theme.peach

@Composable
fun BackgroundGradient(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val brushBackground = Brush.verticalGradient(
            listOf(orange, peach, lightPurple), 0f, size.height.toDp().toPx(), TileMode.Mirror
        )
        drawRect(brushBackground)
    }
}
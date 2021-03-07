/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.timer.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.orange

@Composable
fun DeterminateProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = orange,
    backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
    content: @Composable() () -> Unit
) {

    val drawStyle = remember { Stroke(width = 24.dp.value, cap = StrokeCap.Round) }
    val brush = remember {
        SolidColor(progressColor)
    }

    val brushBackground = remember { SolidColor(backgroundColor) }
    val animateCurrentProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing)
    )

    val progressDegrees = animateCurrentProgress.value * PROGRESS_FULL_DEGREES

    Box {
        Canvas(
            modifier = modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Background of Progress bar
            drawArc(
                brush = brushBackground,
                startAngle = 90f,
                sweepAngle = PROGRESS_FULL_DEGREES,
                useCenter = false,
                style = drawStyle
            )

            // Foreground of progress bar, only drawn to the size of the progress
            drawArc(
                brush = brush,
                startAngle = 270f,
                sweepAngle = progressDegrees,
                useCenter = false,
                style = drawStyle
            )
        }
        Box(modifier = Modifier.align(Alignment.Center)) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewProgressBar() {
    DeterminateProgressBar(
        modifier = Modifier.size(96.dp),
        progress = 0.9f
    ) {
        Text("Hi!")
    }
}

private const val PROGRESS_FULL_DEGREES = 360f

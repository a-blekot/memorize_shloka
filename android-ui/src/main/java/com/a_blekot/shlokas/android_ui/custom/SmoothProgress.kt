package com.a_blekot.shlokas.android_ui.custom

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS

@Composable
fun SmoothProgress(
    current: Int,
    total: Int,
    durationMs: Long,
    modifier: Modifier = Modifier,
    color: Color = colorScheme.primary,
    bgColor: Color = colorScheme.background,
    borderColor: Color = colorScheme.secondaryContainer,
    strokeWidth: Dp = borderS
) {
    val currentProgress: Float by animateFloatAsState(
        targetValue = current.toFloat() / total,
        animationSpec = tween(
            durationMillis = durationMs.toInt(),
            easing = LinearEasing,
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1.0f)
            .background(
                color = bgColor,
                shape = CircleShape
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    color = borderColor,
                    width = strokeWidth,
                    shape = CircleShape
                )
        )

        CircularProgressIndicator(
            progress = currentProgress,
            modifier = Modifier.fillMaxSize(),
            color = color,
            strokeWidth = strokeWidth
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val initialTextStyle = typography.titleLarge
            var textStyle = remember { mutableStateOf(initialTextStyle) }
            var readyToDraw = remember { mutableStateOf(false) }
            Text(
                text = "$current",
                color = color,
                style = textStyle.value,
                textAlign = TextAlign.Center,
                softWrap = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {
                        if (readyToDraw.value) drawContent()
                    }
            )

            Divider(
                modifier = Modifier.padding(horizontal = paddingS),
                color = borderColor,
                thickness = borderS
            )

            Text(
                text = "$total",
                color = color,
                style = textStyle.value,
                textAlign = TextAlign.Center,
                softWrap = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {
                        if (readyToDraw.value) drawContent()
                    },
                onTextLayout = {
                    if (it.didOverflowWidth || it.didOverflowHeight) {
                        textStyle.value = textStyle.value.copy(fontSize = textStyle.value.fontSize * 0.95)
                    } else {
                        readyToDraw.value = true
                    }
                }
            )
        }
    }
}
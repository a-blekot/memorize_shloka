package com.a_blekot.shlokas.android.view.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android.theme.*
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.value.MutableValue

@Composable
fun PlayerView(component: PlayerComponent) {
    val state = component.flow.subscribeAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.background)
            .padding(4.dp)
            .border(
                width = 2.dp,
                color = colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
    ) {

        state.value.run {
            Text(
                text = title,
                color = colorScheme.primary,
                style = typography.headlineLarge
            )
            Text(
                text = sanskrit,
                color = colorScheme.primary,
                style = typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            if (wordsTranslation.isNotBlank()) {
                FoldableView("Words", colorScheme.secondary, colorScheme.onSecondary) {
                    Text(
                        text = wordsTranslation,
                        color = colorScheme.onSecondary,
                        style = typography.titleSmall,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
                    )
                }
            }

            if (translation.isNotBlank()) {
                FoldableView("Translation", colorScheme.secondaryContainer, colorScheme.onSecondaryContainer) {
                    Text(
                        text = translation,
                        color = colorScheme.onSecondaryContainer,
                        style = typography.titleLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(Modifier.weight(1.0f))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(0.3f).aspectRatio(1.0f)
            ) {
                CircularProgressIndicator(
                    progress = currentRepeat.toFloat() / totalRepeats,
                    color = colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = "$currentRepeat/$totalRepeats",
                    color = colorScheme.primary,
                    style = typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                )
            }

            Spacer(Modifier.weight(1.0f))

            Text(
                timeMs.toString(),
                color = colorScheme.primary,
                style = typography.titleMedium
            )
            Text(
                if (isPlaying) "PLAY" else "PAUSE",
                color = colorScheme.primary,
                style = typography.titleMedium
            )
        }
    }
}

@Composable
fun FoldableView(title: String, color: Color, onColor: Color, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val contentIsVisible = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth().background(color = color, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            IconButton(
                onClick = { contentIsVisible.value = !contentIsVisible.value },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    if (contentIsVisible.value)
                        Icons.Rounded.ExpandLess
                    else
                        Icons.Rounded.ExpandMore,
                    "Expand",
                    tint = onColor,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                title,
                color = onColor,
                style = typography.titleSmall
            )
        }
        if (contentIsVisible.value) {
            content()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun PlayerViewPreview() {
    PlayerView(PlayerComponentStub)
}

object PlayerComponentStub : PlayerComponent {
    override val flow = MutableValue(PlayerState(title = "ШБ 1.1.6", timeMs = 32_050L))
}
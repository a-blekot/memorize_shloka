package com.a_blekot.shlokas.android_ui.view.player

import HtmlText
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.SmoothProgress
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartLazyColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderSmall
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeL
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.resources.MR.strings.label_translation
import com.a_blekot.shlokas.common.resources.MR.strings.label_words
import com.a_blekot.shlokas.common.resources.resolve
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

//import com.arkivanov.decompose.value.MutableValue

@Composable
fun PlayerView(component: PlayerComponent) {
    val state = component.flow.subscribeAsState()

    StandartColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(color = colorScheme.background)
            .padding(paddingXS)
            .border(
                width = borderSmall,
                color = colorScheme.primary,
                shape = RoundedCornerShape(radiusM)
            )
    ) {

        state.value.run {
            TitleAndProgress(this)

            HtmlText(
                text = sanskrit,
                color = colorScheme.primary,
                style = typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = paddingXS)
            )

            val wordsAreVisible = remember { mutableStateOf(false) }
            val translationIsVisible = remember { mutableStateOf(false) }
            val context = LocalContext.current
            
            StandartLazyColumn {
                addFoldableView(label_words.resolve(context), words, wordsAreVisible)
                addFoldableView(label_translation.resolve(context), translation, translationIsVisible)
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

            val alpha: Float by animateFloatAsState(
                targetValue = if (isPlaying) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = LinearEasing,
                )
            )
        }
    }
}

fun LazyListScope.addFoldableView(title: String, text: String, contentIsVisible: MutableState<Boolean>) {
    if (text.isNotBlank()) {
        item {
            FoldableView(
                title = title,
                color = colorScheme.secondaryContainer,
                onColor = colorScheme.onSecondaryContainer,
                contentIsVisible = contentIsVisible
            ) {
                HtmlText(
                    text = text,
                    color = colorScheme.onSecondaryContainer,
                    style = typography.titleLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = paddingS)
                        .padding(bottom = paddingS)
                )
            }
        }
    }
}

@Composable
fun TitleAndProgress(state: PlayerState, modifier: Modifier = Modifier) =
    state.run {
        Row(
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(paddingXS)
        ) {
            SmoothProgress(currentRepeat, totalRepeats, durationMs, modifier.size(70.dp), strokeWidth = borderSmall)

            Text(
                text = title,
                color = colorScheme.primary,
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = modifier.weight(1f)
            )

            SmoothProgress(
                currentShlokaIndex,
                totalShlokasCount,
                totalDurationMs / totalShlokasCount,
                modifier = modifier.size(70.dp),
                strokeWidth = borderSmall
            )
        }
    }

@Composable
fun FoldableView(
    title: String,
    color: Color,
    onColor: Color,
    contentIsVisible: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(paddingXS),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth().background(color = color, shape = RoundedCornerShape(paddingS))
    ) {
        StandartRow(
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start),
            modifier = modifier
        ) {
            IconButton(
                onClick = { contentIsVisible.value = !contentIsVisible.value },
                modifier = Modifier.size(iconSizeL),
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
                text = title,
                color = onColor,
                style = typography.titleSmall
            )
        }
        if (contentIsVisible.value) {
            content()
        }
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
//@Composable
//fun PlayerViewPreview() {
//    PlayerView(PlayerComponentStub)
//}
//object PlayerComponentStub : PlayerComponent {
//    override val flow = MutableValue(PlayerState(title = "ШБ 1.1.6", timeMs = 32_050L))
//}
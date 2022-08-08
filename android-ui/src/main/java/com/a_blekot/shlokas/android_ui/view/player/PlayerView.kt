package com.a_blekot.shlokas.android_ui.view.player

import HtmlText
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.SmoothProgress
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartLazyColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderSmall
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeL
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeXL
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingL
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import com.a_blekot.shlokas.common.player_api.PlaybackState
import com.a_blekot.shlokas.common.player_api.PlaybackState.*
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.resources.MR.strings.label_repeats_counter
import com.a_blekot.shlokas.common.resources.MR.strings.label_sanskrit
import com.a_blekot.shlokas.common.resources.MR.strings.label_translation
import com.a_blekot.shlokas.common.resources.MR.strings.label_verses_counter
import com.a_blekot.shlokas.common.resources.MR.strings.label_words
import com.a_blekot.shlokas.common.resources.resolve
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import io.github.aakira.napier.Napier

//import com.arkivanov.decompose.value.MutableValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerView(component: PlayerComponent) {
    val state = component.flow.subscribeAsState()

    StandartColumn(
        verticalArrangement = Arrangement.spacedBy(paddingS),
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
                style = typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = paddingXS)
            )

            if (!isAutoplay && playbackState == IDLE) {
                Spacer(modifier = Modifier.height(paddingS))
                PlayerFAB(component::play)
                Spacer(modifier = Modifier.height(paddingS))
            }

            val wordsAreVisible = remember { mutableStateOf(false) }
            val translationIsVisible = remember { mutableStateOf(false) }
            val context = LocalContext.current
            val translationStyle = typography.titleLarge

            StandartLazyColumn {
                addFoldableView(label_words.resolve(context), words, wordsAreVisible, translationStyle)
                addFoldableView(
                    label_translation.resolve(context),
                    translation,
                    translationIsVisible,
                    translationStyle
                )
            }

            Spacer(Modifier.weight(1.0f))
        }
    }
}

@Composable
private fun PlayerFAB(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    IconButton(
        onClick,
        modifier = Modifier
            .size(iconSizeXL)
            .scale(scale)
            .background(
                color = colorScheme.secondaryContainer,
                shape = RoundedCornerShape(radiusM)
            )
    ) {
        Icon(
            Icons.Rounded.PlayArrow,
            "Play",
            tint = colorScheme.onPrimaryContainer,
            modifier = Modifier.fillMaxSize()
        )
    }
}

fun LazyListScope.addFoldableView(
    title: String,
    text: String,
    contentIsVisible: MutableState<Boolean>,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Start
) {
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
                    style = style,
                    textAlign = textAlign,
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
            modifier = modifier.padding(horizontal = paddingXS).padding(top = paddingXS)
        ) {
            val context = LocalContext.current

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SmoothProgress(currentRepeat, totalRepeats, durationMs, modifier.size(70.dp))

                Text(
                    text = label_repeats_counter.resolve(context),
                    color = colorScheme.primary,
                    style = typography.labelSmall,
                    maxLines = 1,
                    modifier = modifier.padding(paddingXS)
                )
            }

            Text(
                text = title,
                color = colorScheme.primary,
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = modifier.weight(1f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val verseDuration = totalDurationMs / totalShlokasCount
                SmoothProgress(currentShlokaIndex, totalShlokasCount, verseDuration, modifier = modifier.size(70.dp))

                Text(
                    text = label_verses_counter.resolve(context),
                    color = colorScheme.primary,
                    style = typography.labelSmall,
                    maxLines = 1,
                    modifier = modifier.padding(paddingXS)
                )
            }
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
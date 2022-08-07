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
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import com.a_blekot.shlokas.common.player_api.PlaybackState
import com.a_blekot.shlokas.common.player_api.PlaybackState.*
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.resources.MR.strings.label_sanskrit
import com.a_blekot.shlokas.common.resources.MR.strings.label_translation
import com.a_blekot.shlokas.common.resources.MR.strings.label_words
import com.a_blekot.shlokas.common.resources.resolve
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import io.github.aakira.napier.Napier

//import com.arkivanov.decompose.value.MutableValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerView(component: PlayerComponent) {
    val state = component.flow.subscribeAsState()
    val pointingArrowIsVisible = remember { mutableStateOf(state.value.showPointingArrow) }

//    val infiniteTransition = rememberInfiniteTransition()
//    val color by infiniteTransition.animateColor(
//        initialValue = colorScheme.primaryContainer,
//        targetValue = if (state.value.playbackState == IDLE) colorScheme.error else colorScheme.primaryContainer,
//        animationSpec = infiniteRepeatable(
//            animation = tween(700, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )

    Scaffold(
        floatingActionButton = {
            if(!state.value.isAutoplay) (
                FloatingActionButton(
//                    containerColor = color,
                    onClick = {
                        when (state.value.playbackState) {
                            IDLE -> {
                                component.play()
                                pointingArrowIsVisible.value = false
                            }
                            else -> {
                                /** do nothing **/
                            }
                        }
                    }) {
                    PlayerFAB(state.value.playbackState)
                }
            )
        }
    ) { paddings ->
        StandartColumn(
            verticalArrangement = Arrangement.spacedBy(paddingS),
            modifier = Modifier
                .background(color = colorScheme.background)
                .padding(paddingXS)
                .padding(bottom = paddings.calculateBottomPadding())
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
                    modifier = Modifier.fillMaxWidth().padding(end = 40.dp).padding(vertical = 20.dp)
                )

                val wordsAreVisible = remember { mutableStateOf(false) }
                val translationIsVisible = remember { mutableStateOf(false) }
                val context = LocalContext.current
                val translationStyle = typography.titleLarge

                StandartLazyColumn {
//                addFoldableView(
//                    label_sanskrit.resolve(context),
//                    sanskrit,
//                    sanskritIsVisible,
//                    sanskritStyle,
//                    TextAlign.Center
//                )
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
}

//@Composable
//private fun PointingArrow() {
//    StandartRow {
//        Spacer(modifier = Modifier.weight(1f))
//        Divider(
//            color = colorScheme.primary,
//            thickness = borderSmall,
//            modifier = Modifier.weight(2f)
//        )
//        Icon(
//            Icons.Rounded.KeyboardArrowRight,
//            "arrow right",
//            tint = colorScheme.primary,
//            modifier = Modifier.size(iconSizeXL)
//        )
//        Spacer(modifier = Modifier.weight(1f))
//    }
//}

@Composable
private fun PlayerFAB(state: PlaybackState) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (state == IDLE) 1.5f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Icon(
        when (state) {
            IDLE -> Icons.Rounded.PlayArrow
            else -> Icons.Rounded.Stop
        },
        "Playback control",
        tint = colorScheme.onPrimaryContainer,
        modifier = Modifier.size(iconSizeXL)
            .alpha(
                when (state) {
                    IDLE -> 1f
                    PAUSED, PLAYING, STOPPED -> 0.5f
                }
            )
            .scale(scale)
    )
}

//Text(
//timeMs.toString(),
//color = colorScheme.primary,
//style = typography.titleMedium
//)
//Text(
//if (isPlaying) "PLAY" else "PAUSE",
//color = colorScheme.primary,
//style = typography.titleMedium
//)
//
//val alpha: Float by animateFloatAsState(
//    targetValue = if (isPlaying) 1f else 0f,
//    animationSpec = tween(
//        durationMillis = 2000,
//        easing = LinearEasing,
//    )
//)

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
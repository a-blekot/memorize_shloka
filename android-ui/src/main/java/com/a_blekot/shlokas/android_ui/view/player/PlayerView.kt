package com.a_blekot.shlokas.android_ui.view.player

import HtmlText
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.SmoothProgress
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartLazyColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeL
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeM
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeXL
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingZero
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import com.a_blekot.shlokas.common.player_api.PlaybackState
import com.a_blekot.shlokas.common.player_api.PlaybackState.*
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.resources.MR.strings.label_repeats_counter
import com.a_blekot.shlokas.common.resources.MR.strings.label_text_copied
import com.a_blekot.shlokas.common.resources.MR.strings.label_translation
import com.a_blekot.shlokas.common.resources.MR.strings.label_verses_counter
import com.a_blekot.shlokas.common.resources.MR.strings.label_words
import com.a_blekot.shlokas.common.resources.resolve
import com.a_blekot.shlokas.common.utils.showClosePlayerDialog
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerView(component: PlayerComponent) {
    val state = component.flow.subscribeAsState()

    val isClosePlayerDialogVisible = remember { mutableStateOf(false) }

    BackHandler {
        if (showClosePlayerDialog()) {
            isClosePlayerDialogVisible.value = true
        } else {
            component.stop()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        StandartColumn(
            verticalArrangement = Arrangement.spacedBy(paddingS),
            modifier = Modifier
                .background(color = colorScheme.background)
                .padding(paddingXS)
        ) {
            state.value.run {
                TitleAndProgress(this, component)

                val wordsAreVisible = remember { mutableStateOf(false) }
                val translationIsVisible = remember { mutableStateOf(false) }
                val context = LocalContext.current
                val clipboard = LocalClipboardManager.current
                val translationStyle = typography.titleLarge

                StandartLazyColumn {

                    item {
                        HtmlText(
                            text = sanskrit,
                            color = colorScheme.primary,
                            style = typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = paddingXS)
                                .combinedClickable(
                                    onLongClick = { copyToClipboard(context, clipboard, sanskrit.noHtmlTags()) },
                                    onClick = { }
                                ),
//                    autoSize = true
                        )
                    }

                    addFoldableView(label_words.resolve(context), words, wordsAreVisible, translationStyle)
                    addFoldableView(
                        label_translation.resolve(context),
                        translation,
                        translationIsVisible,
                        translationStyle,
                        TextAlign.Justify
                    )
                }

                Spacer(Modifier.weight(1.0f))
            }
        }

        if (isClosePlayerDialogVisible.value) {
            ClosePlayerDialog(
                modifier = Modifier.fillMaxSize(),
                onStop = component::stop,
                onContinue = { isClosePlayerDialogVisible.value = false }
            )
        }
    }
}

@Composable
private fun PlayPauseFAB(
    playbackState: PlaybackState,
    isAutoplay: Boolean,
    component: PlayerComponent,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {
            when (playbackState) {
                PLAYING -> component.forcePause()
                IDLE -> if (!isAutoplay) component.forcePlay()
                FORCE_PAUSED, NO_AUDIO -> component.forcePlay()
                PAUSED -> {
                    /** do nothing **/
                }
            }
        },
        modifier = modifier
            .size(iconSizeXL)
            .background(
                color = colorScheme.secondaryContainer,
                shape = RoundedCornerShape(radiusM)
            )
    ) {
        Icon(
            playbackState.icon,
            "Play/Pause",
            tint = colorScheme.onPrimaryContainer,
            modifier = Modifier.fillMaxSize()
        )
    }
}

private val PlaybackState.icon
    get() = when (this) {
        IDLE, FORCE_PAUSED -> Icons.Rounded.PlayArrow
        NO_AUDIO -> Icons.Rounded.MusicOff
        else -> Icons.Rounded.Pause
    }

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.addFoldableView(
    title: String,
    text: String,
    contentIsVisible: MutableState<Boolean>,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Start
) {
    if (text.isNotBlank()) {
        item {
            val context = LocalContext.current
            val clipboard = LocalClipboardManager.current

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
                        .combinedClickable(
                            onLongClick = { copyToClipboard(context, clipboard, text.noHtmlTags()) },
                            onClick = {}
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TitleAndProgress(state: PlayerState, component: PlayerComponent, modifier: Modifier = Modifier) =
    state.run {
        Row(
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(horizontal = paddingXS).padding(top = paddingXS)
        ) {
            val context = LocalContext.current
            val clipboard = LocalClipboardManager.current

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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.weight(1f)
            ) {
                StandartRow(
                    modifier = Modifier.clickable { copyToClipboard(context, clipboard, state.copyAll()) },
                    horizontalArrangement = Arrangement.Center,
                    padding = paddingZero,
                )
                {
                    Text(
                        text = title,
                        color = colorScheme.primary,
                        style = typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )

                    Icon(
                        Icons.Rounded.ContentCopy,
                        "Copy All",
                        tint = colorScheme.primary,
                        modifier = Modifier.size(iconSizeL)
                    )
                }

                PlayPauseFAB(
                    playbackState = playbackState,
                    isAutoplay = isAutoplay,
                    component = component,
                )


            }

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
            modifier = modifier.clickable { contentIsVisible.value = !contentIsVisible.value }
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

private fun PlayerState.copyAll() =
    "$title\n\n$sanskrit\n\n$words\n\n$translation".noHtmlTags()

private fun copyToClipboard(context: Context, clipboard: ClipboardManager, text: String) {
    clipboard.setText(AnnotatedString(text))
    Toast.makeText(context, label_text_copied.resolve(context), Toast.LENGTH_LONG).show()
}

private fun String.noHtmlTags() =
    this
        .replace("<br>", "\n")
        .replace(Regex("<(?:!|/?[a-zA-Z]+).*?/?>"), "")
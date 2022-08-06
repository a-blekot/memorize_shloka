package com.a_blekot.shlokas.android_ui.view.player

import HtmlText
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.sp
import com.a_blekot.shlokas.android_ui.custom.SmoothProgress
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.resources.MR
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import dev.icerock.moko.resources.desc.desc
import io.github.aakira.napier.Napier

//import com.arkivanov.decompose.value.MutableValue

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

            TitleAndProgress(this)

            val sanskrit =
                "<i>ш́уш́рӯшох̣ ш́раддадха̄насйа<br>ва̄судева-катха̄-ручих̣<br>сйа̄н махат-севайа̄ випра̄х̣<br>пун̣йа-тӣртха-нишеван̣а̄т</i>"

            val context = LocalContext.current

            HtmlText(
                text = MR.strings.sansk_SB_1_2_16.desc().toString(context),
                color = colorScheme.primary,
                style = typography.headlineMedium.copy(lineHeight = 32.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

//            if (words.isNotBlank()) {
            FoldableView("Words", colorScheme.secondaryContainer, colorScheme.onSecondaryContainer) {
//                    Text(
//                        text = words,
//                        color = colorScheme.onSecondary,
//                        style = typography.titleLarge,
//                        textAlign = TextAlign.Start,
//                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
//                    )

                val words =
                    "<i><b>ш́уш́рӯшох̣</b></i> — слушающий;<br><i><b>ш́раддадха̄насйа</b></i> — старательно и внимательно;<br><i><b>ва̄судева</b></i> — относящиеся к Ва̄судеве;<br><i><b>катха̄</b></i> — послания;<br><i><b>ручих̣</b></i> — влечение;<br><i><b>сйа̄т</b></i> — становится возможным;<br><i><b>махат-севайа̄</b></i> — благодаря служению чистым преданным;<br><i><b>випра̄х̣</b></i> — о дваждырожденные;<br><i><b>пун̣йа-тӣртха</b></i> — тем, кто свободен от всех пороков;<br><i><b>нишеван̣а̄т</b></i> — служением."

                HtmlText(
                    text = MR.strings.words_SB_1_2_16.desc().toString(context),
                    color = colorScheme.onSecondaryContainer,
                    style = typography.titleLarge.copy(lineHeight = 32.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                )
            }
//            }

//            if (translation.isNotBlank()) {
            FoldableView("Translation", colorScheme.secondaryContainer, colorScheme.onSecondaryContainer) {
//                    Text(
//                        text = translation,
//                        color = colorScheme.onSecondaryContainer,
//                        style = typography.titleLarge,
//                        textAlign = TextAlign.Start,
//                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
//                    )

                HtmlText(
                    text = MR.strings.trans_SB_1_2_16.desc().toString(context),
                    color = colorScheme.onSecondaryContainer,
                    style = typography.titleLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                )
            }
//            }

            Spacer(Modifier.weight(1.0f))

            //                val infiniteTransition = rememberInfiniteTransition()
//                val alpha by infiniteTransition.animateFloat(
//                    initialValue = 1f,
//                    targetValue = if (hasChanges) 0.5f else 1.0f,
//                    animationSpec = infiniteRepeatable(
//                        animation = tween(1000, easing = LinearEasing),
//                        repeatMode = RepeatMode.Reverse
//                    )
//                )

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

@Composable
fun TitleAndProgress(state: PlayerState, modifier: Modifier = Modifier) =
    state.run {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(4.dp)
        ) {
            SmoothProgress(currentRepeat, totalRepeats, durationMs, modifier.size(70.dp), strokeWidth = 2.dp)

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
                strokeWidth = 2.dp
            )
        }
    }

@Composable
fun FoldableView(
    title: String,
    color: Color,
    onColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
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

//@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
//@Composable
//fun PlayerViewPreview() {
//    PlayerView(PlayerComponentStub)
//}
//object PlayerComponentStub : PlayerComponent {
//    override val flow = MutableValue(PlayerState(title = "ШБ 1.1.6", timeMs = 32_050L))
//}
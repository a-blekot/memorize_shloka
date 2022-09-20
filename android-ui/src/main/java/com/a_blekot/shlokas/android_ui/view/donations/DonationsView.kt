package com.a_blekot.shlokas.android_ui.view.donations

import HtmlText
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.R
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.common.resources.MR.strings.label_donate
import com.a_blekot.shlokas.common.resources.MR.strings.title_BG_18_05
import com.a_blekot.shlokas.common.resources.MR.strings.trans_BG_18_05
import com.a_blekot.shlokas.common.resources.resolve
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.listentoprabhupada.common.donations_api.DonationsComponent

@Composable
fun DonationsView(component: DonationsComponent) {
    val state = component.flow.subscribeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
    ) {
        StandartColumn(
            modifier = Modifier.background(colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingS, alignment = Alignment.CenterVertically)
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = title_BG_18_05.resolve(LocalContext.current),
                color = colorScheme.primary,
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
//
//        HtmlText(
//            text = sansk_BG_18_05.resolve(LocalContext.current),
//            color = colorScheme.primary,
//            style = typography.headlineSmall,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.paddingXS)
//        )

            HtmlText(
                text = trans_BG_18_05.resolve(LocalContext.current),
                color = colorScheme.primary,
                style = typography.titleLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.paddingXS)
            )

            Spacer(modifier = Modifier.weight(1f))

            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = if (state.value.showNamaste) 1f else 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(700, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            StandartRow(modifier = Modifier.scale(scale)) {
                Icon(
                    Icons.Rounded.ExpandMore,
                    "look down",
                    tint = colorScheme.primary,
                    modifier = Modifier.size(Dimens.iconSizeL)
                )

                Text(
                    text = label_donate.resolve(LocalContext.current),
                    color = colorScheme.primary,
                    style = typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )

                Icon(
                    Icons.Rounded.ExpandMore,
                    "look down",
                    tint = colorScheme.primary,
                    modifier = Modifier.size(Dimens.iconSizeL)
                )
            }

            state.value.donations
                .forEach {
                    DonationButton(it) {
                        component.purchase(it)
                    }
                }

            Spacer(modifier = Modifier.weight(1f))
        }

        if (state.value.showNamaste) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.namaste_lottie))
            val progress by animateLottieCompositionAsState(composition)

            LottieAnimation(composition, { progress })
        }
    }
}
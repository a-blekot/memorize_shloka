package com.a_blekot.shlokas.android_ui.view.donations

import HtmlText
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.R
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartLazyColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.custom.resolveString
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingM
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.view.player.addFoldableView
import com.a_blekot.shlokas.common.resources.MR.strings.label_donate
import com.a_blekot.shlokas.common.resources.MR.strings.label_no_connection
import com.a_blekot.shlokas.common.resources.MR.strings.label_translation
import com.a_blekot.shlokas.common.resources.MR.strings.sansk_BG_18_5
import com.a_blekot.shlokas.common.resources.MR.strings.trans_BG_18_5
import com.a_blekot.shlokas.common.resources.MR.strings.title_BG_18_5
import com.a_blekot.shlokas.common.resources.resolve
import com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver.Status.Lost
import com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver.Status.Unavailable
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
        StandartColumn(verticalArrangement = Arrangement.Center) {
            StandartLazyColumn(
                modifier = Modifier
                    .background(colorScheme.background)
                    .weight(20f),
                itemPadding = paddingS,
            ) {
                item {
                    HtmlText(
                        text = resolveString(sansk_BG_18_5),
                        color = colorScheme.primary,
                        style = typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = Dimens.paddingXS)
                            .padding(top = paddingS)
                    )
                }

                item {
                    Text(
                        text = resolveString(trans_BG_18_5) + " (${resolveString(title_BG_18_5)})",
                        color = colorScheme.primary,
                        style = typography.titleLarge,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.paddingXS)
                            .padding(top = paddingS)
                    )
                }

                item {
                    Text(
                        text = resolveString(label_donate),
                        color = colorScheme.primary,
                        style = typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier.padding(bottom = paddingM)
                    )
                }

                items(state.value.donations, key = { it.donationLevel }) {
                    DonationButton(it) {
                        component.purchase(it)
                    }
                }
            }

            if (state.value.connectionStatus == Unavailable || state.value.connectionStatus == Lost) {
                StandartRow(
                    modifier = Modifier
                        .background(colorScheme.errorContainer)
                        .weight(1f),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = resolveString(label_no_connection),
                        color = colorScheme.onErrorContainer,
                        style = typography.titleLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        if (state.value.showNamaste) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.namaste_lottie))
            val progress by animateLottieCompositionAsState(composition)

            LottieAnimation(composition, { progress })
        }
    }
}

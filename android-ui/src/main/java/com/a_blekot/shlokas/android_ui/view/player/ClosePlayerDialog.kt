package com.a_blekot.shlokas.android_ui.view.player

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.custom.*
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.dialogBgColor
import com.a_blekot.shlokas.common.resources.MR.strings.label_close_player_continue
import com.a_blekot.shlokas.common.resources.MR.strings.label_close_player_stop
import com.a_blekot.shlokas.common.resources.MR.strings.label_close_player_subtitle
import com.a_blekot.shlokas.common.resources.MR.strings.label_close_player_title
import com.a_blekot.shlokas.common.resources.MR.strings.label_do_not_show_again
import com.a_blekot.shlokas.common.utils.showClosePlayerDialog

@Composable
fun ClosePlayerDialog(modifier: Modifier, onStop: () -> Unit, onContinue: () -> Unit) {
    BackHandler {
        onContinue.invoke()
    }

    Box(
        modifier = modifier
            .background(dialogBgColor())
            .focusable(true)
            .clickable(true) {},
        contentAlignment = Alignment.Center
    ) {
        SmallColumn(
            modifier = Modifier
                .padding(Dimens.paddingM)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(Dimens.radiusM))
                .background(
                    colorScheme.background,
                )
                .padding(Dimens.paddingS)
                .focusable(true)
                .clickable(true) {},
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingM)
        ) {
            Text(
                text = resolveString(label_close_player_title),
                style = typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = resolveString(label_close_player_subtitle),
                style = typography.titleLarge,
                textAlign = TextAlign.Center,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            )

            StandartRow(
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingM),
            ) {
                val doNotShowAgain = remember { mutableStateOf(false) }

                StandartCheckBox(doNotShowAgain.value) {
                    doNotShowAgain.value = it
                    showClosePlayerDialog = !it
                }

                Text(
                    text = resolveString(label_do_not_show_again),
                    style = typography.titleMedium,
                    color = colorScheme.primary,
                    maxLines = 1,
                )
            }

            StandartRow(
                horizontalArrangement = Arrangement.spacedBy(Dimens.paddingM)
            ) {
                DialogButton(modifier = Modifier.weight(1f), false, label_close_player_stop, onStop)
                DialogButton(modifier = Modifier.weight(1f), true, label_close_player_continue, onContinue)
            }
        }
    }
}

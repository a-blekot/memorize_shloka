package com.a_blekot.shlokas.android_ui.custom

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderS
import com.a_blekot.shlokas.android_ui.theme.Dimens.buttonHeight
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeL
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeM
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingM
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import com.a_blekot.shlokas.android_ui.theme.dialogBgColor
import com.a_blekot.shlokas.common.resources.MR.strings.label_prerating_accept
import com.a_blekot.shlokas.common.resources.MR.strings.label_prerating_close
import com.a_blekot.shlokas.common.resources.MR.strings.label_prerating_subtitle
import com.a_blekot.shlokas.common.resources.MR.strings.label_prerating_title
import com.a_blekot.shlokas.common.resources.resolve
import dev.icerock.moko.resources.StringResource

@Composable
fun PreRatingPopup(modifier: Modifier, onAccept: () -> Unit, onClose: () -> Unit) {
    BackHandler {
        onClose.invoke()
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
                .padding(paddingM)
                .fillMaxWidth()
                .background(
                    colorScheme.background,
                    shape = RoundedCornerShape(radiusM)
                )
                .padding(paddingM)
                .focusable(true)
                .clickable(true) {},
            verticalArrangement = Arrangement.spacedBy(paddingM)
        ) {

            val initialTextStyle = typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
            val textStyle = remember { mutableStateOf(initialTextStyle) }
            val readyToDraw = remember { mutableStateOf(false) }

            Text(
                text = resolveString(label_prerating_title),
                maxLines = 1,
                style = textStyle.value,
                textAlign = TextAlign.Center,
                color = colorScheme.onPrimaryContainer,
                softWrap = false,
                onTextLayout = {
                    if (it.didOverflowWidth) {
                        textStyle.value = textStyle.value.copy(fontSize = textStyle.value.fontSize * 0.9)
                    } else {
                        readyToDraw.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {
                        if (readyToDraw.value) drawContent()
                    },
            )

            Text(
                text = resolveString(label_prerating_subtitle),
                maxLines = 1,
                style = typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth()
            )

            StandartRow(
                horizontalArrangement = Arrangement.spacedBy(paddingS, Alignment.CenterHorizontally),
                padding = paddingM
            ) {
                repeat(5) {
                    StarImage()
                }
            }

            StandartRow(
                horizontalArrangement = Arrangement.spacedBy(paddingM)
            ) {
                DialogButton(modifier = Modifier.weight(1f), false, label_prerating_close, onClose)
                DialogButton(modifier = Modifier.weight(1f), true, label_prerating_accept, onAccept)
            }
        }
    }
}

@Composable
private fun StarImage() {
    Icon(
        Icons.Rounded.Star,
        "star",
        tint = colorScheme.primary,
        modifier = Modifier.size(iconSizeL)
    )
}

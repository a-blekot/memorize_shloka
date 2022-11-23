package com.a_blekot.shlokas.android_ui.view.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.BuildConfig
import com.a_blekot.shlokas.android_ui.custom.SmallColumn
import com.a_blekot.shlokas.android_ui.custom.StandartCheckBox
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingZero
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusS
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.list_api.ListComponent

@Composable
fun ListItemView(config: ShlokaConfig, component: ListComponent, modifier: Modifier = Modifier) {
    StandartRow(
        horizontalArrangement = Arrangement.spacedBy(paddingS),
        padding = 2.dp,
        modifier = modifier
            .background(
                color = colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(radiusS)
            )
            .clickable { component.play(config) }
    ) {
        SmallColumn(
            verticalArrangement = Arrangement.spacedBy(paddingZero),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(50f)
        ) {
            Text(
                config.shloka.title,
                overflow = TextOverflow.Ellipsis,
                color = colorScheme.onPrimaryContainer,
                style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
            )

            Text(
                component.resolveDescription(config.shloka.id),
                overflow = TextOverflow.Ellipsis,
                color = colorScheme.onPrimaryContainer,
                style = typography.titleLarge.copy(fontStyle = FontStyle.Italic),
                maxLines = 1,
            )
        }

        SmallColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            modifier = Modifier.weight(5f)
        ) {
            if (!config.shloka.hasAudio) {
                Icon(
                    Icons.Rounded.MusicOff,
                    "no audio available",
                    tint = colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(Dimens.iconSizeM)
                )
            }

            StandartCheckBox(config.isSelected, color = colorScheme.onPrimaryContainer) {
                component.select(config.shloka.id, it)
            }
        }
    }
}
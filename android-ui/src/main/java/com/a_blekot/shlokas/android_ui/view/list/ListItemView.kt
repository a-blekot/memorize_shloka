package com.a_blekot.shlokas.android_ui.view.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.StandartCheckBox
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusSmall
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.list_api.ListComponent

@Composable
fun ListItemView(index: Int, config: ShlokaConfig, component: ListComponent, modifier: Modifier = Modifier) {
    StandartRow(
        horizontalArrangement = Arrangement.spacedBy(paddingS),
        modifier = modifier
            .background(
                color = colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(radiusSmall)
            )
            .padding(vertical = 10.dp)
            .clickable { component.play(config) }
    ) {
        Text(
            "${index + 1})",
            color = colorScheme.onPrimaryContainer,
            style = typography.titleLarge
        )

        Text(
            config.shloka.title,
            overflow = TextOverflow.Ellipsis,
            color = colorScheme.onPrimaryContainer,
            style = typography.titleLarge,
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1.0f))

        StandartCheckBox(config.isSelected, color = colorScheme.onPrimaryContainer) {
            component.select(config.shloka.id, it)
        }
    }
}
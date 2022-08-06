package com.a_blekot.shlokas.android_ui.view.list_dev

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.StandartCheckBox
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeL
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.list_api.ListComponent

@Composable
fun ListDevItemView(index: Int, config: ShlokaConfig, component: ListComponent, modifier: Modifier = Modifier) {
    StandartRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                color = colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 10.dp)
            .clickable { component.details(config) }
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

        IconButton(
            onClick = { component.moveUp(config.shloka.id) },
            modifier = Modifier.size(iconSizeL),
        ) {
            Icon(
                Icons.Rounded.KeyboardArrowUp,
                "up",
                tint = colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = { component.moveDown(config.shloka.id) },
            modifier = Modifier.size(iconSizeL),
        ) {
            Icon(
                Icons.Rounded.KeyboardArrowDown,
                "down",
                tint = colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxSize()
            )
        }

        StandartCheckBox(config.isSelected, color = colorScheme.onPrimaryContainer) {
            component.select(config.shloka.id, it)
        }
    }
}
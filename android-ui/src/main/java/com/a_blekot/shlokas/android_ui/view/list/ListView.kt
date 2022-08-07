package com.a_blekot.shlokas.android_ui.view.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartLazyColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderSmall
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeXL
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusS
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun ListView(component: ListComponent) {
    val state = component.flow.subscribeAsState()
    StandartColumn(modifier = Modifier.background(colorScheme.background)) {
        Text(
            state.value.config.title,
            color = colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        ButtonsRow(component)

        StandartLazyColumn {
            itemsIndexed(state.value.config.list, key = { _, it -> it.shloka.id }) { index, config ->
                ListItemView(index, config, component)
            }
        }
    }
}

@Composable
private fun ButtonsRow(component: ListComponent, modifier: Modifier = Modifier) {
    StandartRow(
        modifier = modifier
            .border(
                width = borderSmall,
                color = colorScheme.primary,
                shape = RoundedCornerShape(radiusS)
            )
    ) {
        IconButton(
            onClick = { component.play() },
            modifier = Modifier.size(iconSizeXL),
        ) {
            Icon(
                Icons.Rounded.PlayCircle,
                "play",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = { component.settings() },
            modifier = Modifier.size(iconSizeXL),
        ) {
            Icon(
                Icons.Rounded.Settings,
                "settings",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

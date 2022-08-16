package com.a_blekot.shlokas.android_ui.view.list_dev

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartLazyColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeL
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusS
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun ListDevView(component: ListComponent) {
    val state = component.flow.subscribeAsState()
    StandartColumn(modifier = Modifier.background(colorScheme.background)) {
        ButtonsRow(state.value.hasChanges, component, modifier = Modifier.padding(12.dp))
        Text(
            state.value.config.title,
            color = colorScheme.primary,
            style = typography.headlineLarge,
            modifier = Modifier.padding(top = 20.dp)
        )

        StandartLazyColumn {
            itemsIndexed(state.value.config.list, key = { _, it -> it.shloka.id }) { index, config ->
                ListDevItemView(index, config, component)
            }
        }
    }
}

@Composable
private fun ButtonsRow(listHasChanges: Boolean, component: ListComponent, modifier: Modifier = Modifier) {
    StandartRow(
        modifier = modifier
            .border(
                width = Dimens.borderS,
                color = colorScheme.primary,
                shape = RoundedCornerShape(radiusS)
            )
    ) {

        fun removeShloka() {
            component.flow.value.config.list.lastOrNull()?.let {
                component.remove(it.shloka.id)
            }
        }

        IconButton(
            onClick = { component.add() },
            modifier = modifier.size(iconSizeL),
        ) {
            Icon(
                Icons.Rounded.AddBox,
                "add shloka",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = { removeShloka() },
            modifier = modifier.size(iconSizeL),
        ) {
            Icon(
                Icons.Rounded.Delete,
                "remove shloka",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        val infiniteTransition = rememberInfiniteTransition()
        val alpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = if (listHasChanges) 0.5f else 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        IconButton(
            enabled = listHasChanges,
            onClick = { component.save() },
            modifier = modifier
                .size(iconSizeL)
                .alpha(alpha),
        ) {
            Icon(
                Icons.Rounded.Save,
                "save list",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = { component.play() },
            modifier = Modifier.size(iconSizeL),
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
            modifier = Modifier.size(iconSizeL),
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

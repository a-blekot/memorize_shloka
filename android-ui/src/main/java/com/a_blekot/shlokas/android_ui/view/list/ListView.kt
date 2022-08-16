package com.a_blekot.shlokas.android_ui.view.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.custom.*
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderS
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeXL
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusS
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import io.github.aakira.napier.Napier

@Composable
fun ListView(component: ListComponent) {
    val state = component.flow.subscribeAsState()

    val menuIsVisible = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        StandartColumn(modifier = Modifier.background(colorScheme.background)) {
            Text(
                state.value.config.title,
                color = colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )

            ButtonsRow(component, onListClick = { menuIsVisible.value = true })

            StandartLazyColumn {
                itemsIndexed(state.value.config.list, key = { _, it -> it.shloka.id }) { index, config ->
                    ListItemView(index, config, component)
                }
            }
        }

        if (menuIsVisible.value && state.value.availableLists.isNotEmpty()) {
            ChooseList(state.value.availableLists, modifier = Modifier.fillMaxSize()) { id ->
                menuIsVisible.value = false
                component.setList(id)
            }
        }

        if (state.value.shouldShowTutorial) {
            InfoPopup(
                ftueInfo(state.value.locale),
                modifier = Modifier.fillMaxSize(),
                onSkip = component::onTutorialSkipped,
                onComplete = component::onTutorialCompleted
            )
        }
    }
}

@Composable
private fun ButtonsRow(component: ListComponent, onListClick: () -> Unit, modifier: Modifier = Modifier) {
    StandartRow(
        modifier = modifier
            .border(
                width = borderS,
                color = colorScheme.primary,
                shape = RoundedCornerShape(radiusS)
            )
    ) {
        IconButton(
            onClick = onListClick,
            modifier = Modifier.size(iconSizeXL),
        ) {
            Icon(
                Icons.Rounded.MenuBook,
                "select list",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

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

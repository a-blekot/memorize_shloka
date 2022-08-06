package com.a_blekot.shlokas.android_ui.view.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Today
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.InfoPopup
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingM
import com.a_blekot.shlokas.android_ui.theme.textFieldColors
import com.a_blekot.shlokas.common.settings_api.SettingsComponent
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(component: SettingsComponent) {
    val state = component.flow.subscribeAsState()
    val infoIsVisible = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(horizontal = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            val repeats = remember { mutableStateOf(TextFieldValue(text = state.value.repeats.toString())) }

            OutlinedTextField(
                value = repeats.value,
                onValueChange = {
                    repeats.value = it
                    component.setRepeats(it.text.toIntOrNull() ?: 1)
                },
                textStyle = typography.titleLarge,
                maxLines = 1,
                label = { Text(text = "repeats") },
                colors = textFieldColors(),
                placeholder = { Text(text = "enter some number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Repeat,
                        tint = colorScheme.primary,
                        contentDescription = "Repeat"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            val weeks = remember { mutableStateOf(TextFieldValue(text = state.value.week.ordinal.toString())) }

            OutlinedTextField(
                value = weeks.value,
                onValueChange = {
                    weeks.value = it
                    component.setWeek(it.text.toIntOrNull() ?: 0)
                },
                textStyle = typography.titleLarge,
                maxLines = 1,
                label = { Text(text = "week") },
                colors = textFieldColors(),
                placeholder = { Text(text = "1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Today,
                        tint = colorScheme.primary,
                        contentDescription = "Week"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            StandartRow(
                horizontalArrangement = Arrangement.spacedBy(paddingM),
                modifier = Modifier.clickable { infoIsVisible.value = true }
            ) {
                Icon(
                    Icons.Rounded.Info,
                    "Info",
                    tint = colorScheme.primary,
                    modifier = Modifier.size(Dimens.iconSizeL)
                )

                Text(
                    text = "Show tutorial",
                    style = typography.titleLarge,
                    color = colorScheme.primary,
                    maxLines = 1,
                )
            }
        }

        if (infoIsVisible.value) {
            InfoPopup(modifier = Modifier.fillMaxSize()) {
                infoIsVisible.value = false
            }
        }

    }
}


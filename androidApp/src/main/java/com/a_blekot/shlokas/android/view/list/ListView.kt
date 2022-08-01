package com.a_blekot.shlokas.android.view.list

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddBox
import androidx.compose.material.icons.rounded.CalendarViewWeek
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android.theme.textFieldColors
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.a_blekot.shlokas.common.utils.getCurrentWeek
import com.a_blekot.shlokas.common.utils.getRepeats
import com.a_blekot.shlokas.common.utils.saveCurrentWeek
import com.a_blekot.shlokas.common.utils.saveRepeats
import com.a_blekot.shlokas.common.utils.weekFromOrdinal
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import io.github.aakira.napier.Napier

@Composable
fun ListView(component: ListComponent) {
    val state = component.flow.subscribeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Napier.w("hasChanges = ${state.value.hasChanges}", tag = "LILIS")
            ButtonsRow(state.value.hasChanges, component, modifier = Modifier.padding(12.dp))

            Text(
                state.value.config.title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                state.value.config.description,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
            )

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(state.value.config.list, key = { _, it -> it.shloka.id }) { index, config ->
                    ListItemView(index, config, component)
                }


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ButtonsRow(listHasChanges: Boolean, component: ListComponent, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {

            fun removeShloka() {
                component.flow.value.config.list.lastOrNull()?.let {
                    component.remove(it.shloka.id)
                }
            }

            IconButton(
                onClick = { component.add() },
                modifier = modifier.size(48.dp),
            ) {
                Icon(
                    Icons.Rounded.AddBox,
                    "add shloka",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }

            IconButton(
                onClick = { removeShloka() },
                modifier = modifier.size(48.dp),
            ) {
                Icon(
                    Icons.Rounded.Delete,
                    "remove shloka",
                    tint = MaterialTheme.colorScheme.primary,
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
                    .size(48.dp)
                    .alpha(alpha),
            ) {
                Icon(
                    Icons.Rounded.Save,
                    "save list",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }

            IconButton(
                onClick = { component.play() },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    Icons.Rounded.PlayCircle,
                    "play",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(bottom = 4.dp)
        ) {
            val repeats = remember { mutableStateOf(TextFieldValue(text = getRepeats().toString())) }
            val hasChanges = repeats.value.text != getRepeats().toString()

            OutlinedTextField(
                value = repeats.value,
                onValueChange = { repeats.value = it },
                textStyle = MaterialTheme.typography.titleLarge,
                maxLines = 1,
//                label = { Text(text = "repeats") },
                colors = textFieldColors(),
                placeholder = { Text(text = "10") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Repeat,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Repeat"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (hasChanges)
                            Icons.Rounded.CheckBoxOutlineBlank
                        else
                            Icons.Rounded.CheckBox,
                        contentDescription = "check icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = modifier.clickable {
                            saveRepeats(repeats.value.text.toInt())
                        }
                    )
                },
                modifier = modifier
                    .weight(2.0f)
            )

            val weeks = remember { mutableStateOf(TextFieldValue(text = getCurrentWeek().ordinal.toString())) }
            val weeksHasChanges = weeks.value.text != getCurrentWeek().toString()

            OutlinedTextField(
                value = weeks.value,
                onValueChange = { weeks.value = it },
                textStyle = MaterialTheme.typography.titleLarge,
                maxLines = 1,
//                label = { Text(text = "week") },
                colors = textFieldColors(),
                placeholder = { Text(text = "1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Today,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Week"
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (weeksHasChanges)
                            Icons.Rounded.CheckBoxOutlineBlank
                        else
                            Icons.Rounded.CheckBox,
                        contentDescription = "check icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = modifier.clickable {
                            saveCurrentWeek(weekFromOrdinal(weeks.value.text.toInt()))
                        }
                    )
                },
                modifier = modifier
                    .weight(1.5f)
            )
        }
    }
}
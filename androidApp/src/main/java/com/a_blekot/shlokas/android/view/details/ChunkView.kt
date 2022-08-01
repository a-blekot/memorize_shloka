package com.a_blekot.shlokas.android.view.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android.theme.textFieldColors
import com.a_blekot.shlokas.common.data.Chunk
import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.google.accompanist.insets.LocalWindowInsets
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChunkView(index: Int, chunk: Chunk, component: DetailsComponent, modifier: Modifier = Modifier) {
    val relocationRequester = remember { BringIntoViewRequester() }
    var focused = remember { mutableStateOf(false) }
    val ime = LocalWindowInsets.current.ime

    LaunchedEffect(focused) {
        if (focused.value) {
            var done = false
            while (!done) {
                if (ime.isVisible && !ime.animationInProgress) {
                    relocationRequester.bringIntoView()
                    done = true
                }
                delay(100L)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        val startState = remember { mutableStateOf(TextFieldValue(text = chunk.startMs.toString())) }
        val startHasChanges = startState.value.text != chunk.startMs.toString()

        OutlinedTextField(
            value = startState.value,
            onValueChange = { startState.value = it },
            textStyle = MaterialTheme.typography.titleMedium,
            label = { Text(text = "start, ms") },
            colors = textFieldColors(),
            placeholder = { Text(text = "1500") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Timer,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "timer icon"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = if (startHasChanges)
                        Icons.Rounded.CheckBoxOutlineBlank
                    else
                        Icons.Rounded.CheckBox,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "check icon",
                    modifier = modifier.clickable {
                        if (startHasChanges) {
                            component.setChunkStart(index, startState.value.text.toLong())
                        }
                    }
                )
            },
            modifier = modifier.onFocusChanged { focused.value = it.hasFocus }
        )


        val endState = remember { mutableStateOf(TextFieldValue(text = chunk.endMs.toString())) }
        val endHasChanges = endState.value.text != chunk.endMs.toString()

        OutlinedTextField(
            value = endState.value,
            onValueChange = { endState.value = it },
            textStyle = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            label = { Text(text = "end, ms") },
            colors = textFieldColors(),
            placeholder = { Text(text = "12000") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Timer,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "timer icon"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = if (endHasChanges)
                        Icons.Rounded.CheckBoxOutlineBlank
                    else
                        Icons.Rounded.CheckBox,
                    contentDescription = "check icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier.clickable {
                        if (endHasChanges) {
                            component.setChunkEnd(index, endState.value.text.toLong())
                        }
                    }
                )
            },
            modifier = modifier.onFocusChanged { focused.value = it.hasFocus }
        )
    }
}
package com.a_blekot.shlokas.android_ui.view.player

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeM
import com.a_blekot.shlokas.common.data.RepeatMode
import com.a_blekot.shlokas.common.data.RepeatMode.FOUR_LINES
import com.a_blekot.shlokas.common.data.RepeatMode.ONE_LINE
import com.a_blekot.shlokas.common.data.RepeatMode.QUICK_LEARN
import com.a_blekot.shlokas.common.data.RepeatMode.TWO_LINES
import com.a_blekot.shlokas.common.resources.MR.strings.label_four_lines
import com.a_blekot.shlokas.common.resources.MR.strings.label_one_line
import com.a_blekot.shlokas.common.resources.MR.strings.label_quick_learn
import com.a_blekot.shlokas.common.resources.MR.strings.label_repeat_mode
import com.a_blekot.shlokas.common.resources.MR.strings.label_two_lines
import com.a_blekot.shlokas.common.resources.resolve
import dev.icerock.moko.resources.StringResource

@Composable
internal fun RepeatModeDropDown(
    currentMode: RepeatMode,
    modifier: Modifier = Modifier,
    onRepeatModeChanged: (RepeatMode) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            border = BorderStroke(1.dp, colorScheme.onSecondaryContainer),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = colorScheme.secondaryContainer,
                contentColor = colorScheme.onSecondaryContainer,
            )
        ) {
            Text(
                text = label_repeat_mode.resolve(LocalContext.current),
                style = typography.titleSmall,
                color = colorScheme.onSecondaryContainer,
            )
        }

        DropdownMenu(
            expanded = expanded,
            modifier = Modifier.background(colorScheme.secondaryContainer),
            onDismissRequest = { expanded = false }
        ) {
            RepeatMode.entries.forEachIndexed { index, repeatMode ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = repeatMode.title.resolve(LocalContext.current),
                            style = typography.titleSmall,
                            color = colorScheme.onSecondaryContainer,
                        )
                    },
                    trailingIcon = selectedIcon(repeatMode == currentMode),
                    onClick = {
                        expanded = false
                        if (repeatMode != currentMode) {
                            onRepeatModeChanged(repeatMode)
                        }
                    }
                )

                if (index != RepeatMode.entries.lastIndex) {
                    HorizontalDivider(color = colorScheme.onSecondaryContainer)
                }
            }
        }
    }
}

private fun selectedIcon(isSelected: Boolean): @Composable (() -> Unit)? =
    if (!isSelected) {
        null
    } else {
        {
            Icon(
                Icons.Rounded.Check,
                "isSelected",
                tint = colorScheme.primary,
                modifier = Modifier.size(iconSizeM)
            )
        }
    }

private val RepeatMode.title: StringResource
    get() = when (this) {
        ONE_LINE -> label_one_line
        TWO_LINES -> label_two_lines
        FOUR_LINES -> label_four_lines
        QUICK_LEARN -> label_quick_learn
    }

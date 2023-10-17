package com.a_blekot.shlokas.android_ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun textFieldColors(
    textColor: Color = MaterialTheme.colorScheme.primary
) =
    OutlinedTextFieldDefaults.colors(
          unfocusedTextColor = textColor,
    )
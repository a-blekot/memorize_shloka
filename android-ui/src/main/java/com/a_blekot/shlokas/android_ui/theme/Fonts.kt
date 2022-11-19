package com.a_blekot.shlokas.android_ui.theme

import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.a_blekot.shlokas.android_ui.R

private val fontFamily = FontFamily(
    Font(R.font.noto_sans),
    Font(R.font.noto_sans_b, weight = FontWeight.Bold),
    Font(R.font.noto_sans_i, style = FontStyle.Italic),
    Font(R.font.noto_sans_bi, weight = FontWeight.Bold, style = FontStyle.Italic),
)

@Composable
fun typography() =
    typography.copy(
        displayLarge = typography.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = typography.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = typography.displaySmall.copy(fontFamily = fontFamily),

        headlineLarge = typography.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = typography.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = typography.headlineSmall.copy(fontFamily = fontFamily),

        titleLarge = typography.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = typography.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = typography.titleSmall.copy(fontFamily = fontFamily),

        bodyLarge = typography.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = typography.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = typography.bodySmall.copy(fontFamily = fontFamily),

        labelLarge = typography.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = typography.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = typography.labelSmall.copy(fontFamily = fontFamily),
    )

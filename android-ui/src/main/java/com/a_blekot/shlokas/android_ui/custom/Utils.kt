package com.a_blekot.shlokas.android_ui.custom

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.a_blekot.shlokas.common.resources.resolve
import dev.icerock.moko.resources.StringResource


@Composable
fun resolveString(stringResource: StringResource) =
    stringResource.resolve(LocalContext.current)
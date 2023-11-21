package com.a_blekot.shlokas.android_ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun @receiver:StringRes Int.resource(): String =
    resource(
        formatArgs = emptyArray(),
    )

@Composable
fun @receiver:StringRes Int.resource(vararg formatArgs: Any): String =
    stringResource(
        id = this,
        formatArgs = formatArgs,
    )

@Composable
fun @receiver:DrawableRes Int.painter(): Painter =
    painterResource(this)

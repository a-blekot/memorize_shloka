package com.a_blekot.shlokas.android_ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.Spacer(height: Dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun RowScope.Spacer(width: Dp) = Spacer(modifier = Modifier.width(width))

@Composable
fun RowScope.Spacer(weight: Float) = Spacer(modifier = Modifier.weight(weight))

@Composable
fun ColumnScope.Spacer(weight: Float) = Spacer(modifier = Modifier.weight(weight))

@Composable
fun LazyItemScope.Spacer(height: Dp) = Spacer(modifier = Modifier.height(height))

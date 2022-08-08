package com.a_blekot.memorize_shloka

import androidx.compose.runtime.Composable
import com.a_blekot.shlokas.android_ui.view.list.ListView
import com.a_blekot.shlokas.android_ui.view.player.PlayerView
import com.a_blekot.shlokas.android_ui.view.settings.SettingsView
import com.a_blekot.shlokas.common.root.RootComponent
import com.a_blekot.shlokas.common.root.RootComponent.Child.*
import com.a_blekot.shlokas.common.root.RootComponent.Child.List
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainContent(component: RootComponent) {
    Children(stack = component.childStack, animation = stackAnimation(fade() + scale())) {
        when (val child = it.instance) {
            is List -> ListView(child.component)
            is Player -> PlayerView(child.component)
            is Settings -> SettingsView(child.component)
            else -> throw IllegalArgumentException("No View for child: ${child.javaClass.simpleName}")
        }
    }
}
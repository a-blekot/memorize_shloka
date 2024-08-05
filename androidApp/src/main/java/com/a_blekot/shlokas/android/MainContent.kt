package com.a_blekot.shlokas.android

import androidx.compose.runtime.Composable
import com.a_blekot.shlokas.android_ui.view.details.DetailsView
import com.a_blekot.shlokas.android_ui.view.list_dev.ListDevView
import com.a_blekot.shlokas.android_ui.view.player.PlayerView
import com.a_blekot.shlokas.android_ui.view.settings.SettingsView
import com.a_blekot.shlokas.common.root.RootComponent
import com.a_blekot.shlokas.common.root.RootComponent.Child.*
import com.a_blekot.shlokas.common.root.RootComponent.Child.List
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
fun MainContent(component: RootComponent) {
    Children(stack = component.childStack, animation = stackAnimation(fade() + scale())) {
        when (val child = it.instance) {
            is List -> ListDevView(child.component)
            is Player -> PlayerView(child.component)
            is Details -> DetailsView(child.component)
            is Settings -> SettingsView(child.component)
        }
    }
}
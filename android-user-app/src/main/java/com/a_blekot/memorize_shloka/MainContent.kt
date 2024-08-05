package com.a_blekot.memorize_shloka

import androidx.compose.runtime.Composable
import com.a_blekot.shlokas.android_ui.view.donations.DonationsView
import com.a_blekot.shlokas.android_ui.view.list.ListView
import com.a_blekot.shlokas.android_ui.view.player.PlayerView
import com.a_blekot.shlokas.android_ui.view.settings.SettingsView
import com.ablekot.shlokas.common.root.RootComponent
import com.ablekot.shlokas.common.root.RootComponent.Child.*
import com.ablekot.shlokas.common.root.RootComponent.Child.List
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
fun MainContent(component: RootComponent) {
    Children(stack = component.childStack, animation = stackAnimation(fade() + scale())) {
        when (val child = it.instance) {
            is List -> ListView(child.component)
            is Player -> PlayerView(child.component)
            is Settings -> SettingsView(child.component)
            is Donations -> DonationsView(child.component)
            else -> throw IllegalArgumentException("No View for child: ${child.javaClass.simpleName}")
        }
    }
}

package com.a_blekot.shlokas.android

import androidx.compose.runtime.Composable
import com.a_blekot.shlokas.android.view.list.ListView
import com.a_blekot.shlokas.android.view.details.DetailsView
import com.a_blekot.shlokas.android.view.player.PlayerView
import com.a_blekot.shlokas.common.root.RootComponent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.plus
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.scale

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainContent(component: RootComponent) {
    Children(routerState = component.routerState, animation = childAnimation(fade() + scale())) {
        when (val child = it.instance) {
            is RootComponent.Child.List -> ListView(child.component)
            is RootComponent.Child.Player -> PlayerView(child.component)
            is RootComponent.Child.Details -> DetailsView(child.component)
        }
    }
}
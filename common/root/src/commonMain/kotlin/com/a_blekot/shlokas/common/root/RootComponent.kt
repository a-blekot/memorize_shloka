package com.a_blekot.shlokas.common.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.arkivanov.decompose.Child

interface RootComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed interface Child {
        data class List(val component: ListComponent) : Child
        data class Player(val component: PlayerComponent) : Child
        data class Details(val component: DetailsComponent) : Child
    }
}
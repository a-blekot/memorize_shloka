package com.ablekot.shlokas.common.root

import com.arkivanov.decompose.value.Value
import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.settings_api.SettingsComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.listentoprabhupada.common.donations_api.DonationsComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class List(val component: ListComponent) : Child
        data class Player(val component: PlayerComponent) : Child
        data class Details(val component: DetailsComponent) : Child
        data class Settings(val component: SettingsComponent) : Child
        data class Donations(val component: DonationsComponent) : Child
    }
}
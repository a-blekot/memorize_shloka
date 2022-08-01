package com.a_blekot.shlokas.common.player_impl.store

import com.a_blekot.shlokas.common.player_api.PlayerState
import com.arkivanov.mvikotlin.core.store.Store

internal interface PlayerStore : Store<PlayerIntent, PlayerState, PlayerLabel>

package com.a_blekot.shlokas.common.player_api


sealed interface PlayerOutput {
    object Stop: PlayerOutput
}
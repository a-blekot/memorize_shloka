package com.a_blekot.shlokas.common.player_impl.store

import com.a_blekot.shlokas.common.data.tasks.Task

sealed interface PlayerLabel {
    object Stop: PlayerLabel
    data class PlayerTask(val task: Task): PlayerLabel
}
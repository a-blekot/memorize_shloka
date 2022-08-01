package com.a_blekot.shlokas.common.player_api

import com.a_blekot.shlokas.common.data.tasks.Task
import kotlinx.coroutines.flow.Flow

interface PlayerBus {
    fun update(task: Task)
    fun update(feedback: PlayerFeedback)

    fun observeTasks(): Flow<Task>
    fun observeFeedback(): Flow<PlayerFeedback>
}
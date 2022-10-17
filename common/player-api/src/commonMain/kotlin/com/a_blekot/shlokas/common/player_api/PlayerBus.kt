package com.a_blekot.shlokas.common.player_api

import com.a_blekot.shlokas.common.data.tasks.Task
import kotlinx.coroutines.CoroutineScope

interface PlayerBus {
    fun update(task: Task)
    fun update(feedback: PlayerFeedback)

    fun observeTasks(onEach: (Task) -> Unit, scope: CoroutineScope? = null)
    fun observeFeedback(onEach: (PlayerFeedback) -> Unit, scope: CoroutineScope? = null)
}

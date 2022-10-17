package com.a_blekot.shlokas.common.player_impl

import com.a_blekot.shlokas.common.data.tasks.Task
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.player_api.PlayerFeedback
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class PlayerBusImpl(
    dispatchers: DispatcherProvider
) : PlayerBus {

    private val scope = CoroutineScope(dispatchers.main) + SupervisorJob()

    private val playerTaskFlow = MutableSharedFlow<Task>()
    private val playerFeedbackFlow = MutableSharedFlow<PlayerFeedback>()

    override fun update(task: Task) {
        scope.launch { playerTaskFlow.emit(task) }
    }

    override fun update(feedback: PlayerFeedback) {
        scope.launch { playerFeedbackFlow.emit(feedback) }
    }

    override fun observeTasks(onEach: (Task) -> Unit, scope: CoroutineScope?) {
        playerTaskFlow
            .onEach { onEach(it) }
            .launchIn(scope ?: this.scope)
    }

    override fun observeFeedback(onEach: (PlayerFeedback) -> Unit, scope: CoroutineScope?) {
        playerFeedbackFlow
            .onEach { onEach(it) }
            .launchIn(scope ?: this.scope)
    }
}
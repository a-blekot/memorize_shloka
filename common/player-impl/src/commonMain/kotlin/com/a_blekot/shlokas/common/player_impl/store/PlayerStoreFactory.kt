package com.a_blekot.shlokas.common.player_impl.store

import com.a_blekot.shlokas.common.data.createTasks
import com.a_blekot.shlokas.common.data.tasks.PauseTask
import com.a_blekot.shlokas.common.data.tasks.PlayTask
import com.a_blekot.shlokas.common.data.tasks.SetTrackTask
import com.a_blekot.shlokas.common.data.tasks.StopTask
import com.a_blekot.shlokas.common.data.tasks.Task
import com.a_blekot.shlokas.common.player_api.PlayerFeedback
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.player_impl.PlayerDeps
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Play
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Pause
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Restart
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Stop
import com.a_blekot.shlokas.common.player_impl.store.PlayerLabel.PlayerTask
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory.Action.Feedback
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory.Action.Start
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val TIMER_INTERVAL_MS = 100L

internal class PlayerStoreFactory(
    private val storeFactory: StoreFactory,
    private val deps: PlayerDeps
) {

    init {
        check(deps.config.shlokas.isNotEmpty()) {
            "PlayConfig can't be empty!"
        }
    }

    private val tasks = deps.config.createTasks()
    private val durationMs = tasks.sumOf { it.duration }
    private val firstShloka = deps.config.shlokas.first().shloka
    private val initialState =
        PlayerState(
            title = firstShloka.title,
            filePath = firstShloka.filePath,
            sanskrit = firstShloka.sanskrit,
            timeMs = 0,
            durationMs = durationMs,
            isPlaying = false
        )

    fun create(): PlayerStore =
        object : PlayerStore, Store<PlayerIntent, PlayerState, PlayerLabel> by storeFactory.create(
            name = "PlayerStore",
            autoInit = false,
            initialState = initialState,
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl()
        ) {}

    sealed interface Action {
        object Start : Action
        data class Feedback(val feedback: PlayerFeedback) : Action
    }

    sealed interface Msg {
        object Play : Msg
        object Pause : Msg
        data class NextRepeat(val timeMs: Long, val currentRepeat: Int, val durationMs: Long) : Msg
        data class Update(
            val title: String,
            val filePath: String,
            val sanskrit: String,
            val wordsTranslation: String,
            val translation: String,
            val totalRepeats: Int
        ) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Start)
            }

            scope.launch {
                deps.playerBus.observeFeedback()
                    .onEach {
                        Napier.w("PlayerFeedback", tag = "PlayerStore")
                        dispatch(Feedback(it))
                    }
                    .launchIn(scope)
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<PlayerIntent, Action, PlayerState, Msg, PlayerLabel>() {

        private var iterator = tasks.iterator()

        override fun executeAction(action: Action, getState: () -> PlayerState) {
            when (action) {
                Start -> start()
                is Feedback -> feedback(action.feedback)
            }
        }

        override fun executeIntent(intent: PlayerIntent, getState: () -> PlayerState) {
            when (intent) {
                Play -> dispatch(Msg.Play)
                Pause -> dispatch(Msg.Pause)
                Restart -> start()
                Stop -> stop()
            }
        }

        private fun start() {
            Napier.d("tasks = $tasks", tag = "TASKS")
            iterator = tasks.iterator()
            nextTask()
        }

        private fun nextTask() {
            if (iterator.hasNext()) {
                Napier.w("nextTask", tag = "PlayerStore")
                scope.launch {
                    handleTask(iterator.next())
                }
            }
        }

        private fun feedback(feedback: PlayerFeedback) {
            when (feedback) {
                PlayerFeedback.Ready -> nextTask()
                PlayerFeedback.Started -> {}
                PlayerFeedback.Completed -> nextTask()
            }
        }

        private fun stop() {
            dispatch(Msg.Pause)
            publish(PlayerLabel.Stop)
        }

        suspend fun handleTask(task: Task) {
            publish(PlayerTask(task))
            when (task) {
                is PlayTask -> {
                    dispatch(Msg.Play)
                    task.run {
                        dispatch(Msg.NextRepeat(absoluteStartMs, currentRepeat, duration))
                    }
                }
                is PauseTask -> pause(task)
                is SetTrackTask -> dispatch(
                    Msg.Update(
                        task.title,
                        task.filePath,
                        task.sanskrit,
                        task.wordsTranslation,
                        task.translation,
                        deps.config.repeats
                    )
                )
                is StopTask -> stop()
            }
        }

        private suspend fun pause(task: PauseTask) {
            dispatch(Msg.Pause)
            delay(task.duration)
            nextTask()
        }

//        private fun playbackStarted() =
//            (currentTask as? PlayTask)?.run {
//                Napier.w("PlayerFeedback.Started", tag = "PlayerStore")
//                scope.launch {
//                    var timeMs = absoluteStartMs
//                    val endTimeMs = timeMs + duration
//
//                    while (endTimeMs > timeMs) {
//                        dispatch(Msg.Time(timeMs))
//                        val interval = TIMER_INTERVAL_MS.coerceAtMost(endTimeMs - timeMs)
//                        delay(interval)
//                        timeMs += interval
//                    }
//                }
//            }
    }

    private inner class ReducerImpl : Reducer<PlayerState, Msg> {
        override fun PlayerState.reduce(msg: Msg): PlayerState =
            when (msg) {
                Msg.Play -> copy(isPlaying = true)
                Msg.Pause -> copy(isPlaying = false)
                is Msg.NextRepeat -> copy(
                    timeMs = msg.timeMs,
                    currentRepeat = msg.currentRepeat,
                    durationMs = msg.durationMs
                )
                is Msg.Update -> copy(
                    title = msg.title,
                    filePath = msg.filePath,
                    sanskrit = msg.sanskrit,
                    wordsTranslation = msg.wordsTranslation,
                    translation = msg.translation,
                    totalRepeats = msg.totalRepeats,
                )
            }
    }
}

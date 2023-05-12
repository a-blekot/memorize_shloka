package com.a_blekot.shlokas.common.player_impl.store

import com.a_blekot.shlokas.common.data.speed
import com.a_blekot.shlokas.common.data.tasks.*
import com.a_blekot.shlokas.common.player_api.PlaybackState.*
import com.a_blekot.shlokas.common.player_api.PlayerFeedback
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.player_impl.PlayerDeps
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.*
import com.a_blekot.shlokas.common.player_impl.store.PlayerLabel.PlayerTask
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory.Action.Feedback
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory.Action.Start
import com.a_blekot.shlokas.common.utils.analytics.playCompleted
import com.a_blekot.shlokas.common.utils.autoPlay
import com.a_blekot.shlokas.common.utils.onPlayCompleted
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
import com.a_blekot.shlokas.common.utils.audioSpeed
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class PlayerStoreFactory(
    private val storeFactory: StoreFactory,
    private val tasks: List<Task>,
    private val durationMs: Long,
    private val initialState: PlayerState,
    private val deps: PlayerDeps
) : StringResourceHandler by deps.stringResourceHandler {

    init {
        check(deps.config.shlokas.any { it.isSelected }) {
            "PlayerStoreFactory: PlayConfig can't be empty!"
        }
        check(tasks.isNotEmpty()) {
            "PlayerStoreFactory: tasks can't be empty!"
        }
    }

    fun create(): PlayerStore {
        return object : PlayerStore, Store<PlayerIntent, PlayerState, PlayerLabel> by storeFactory.create(
            name = "PlayerStore",
            autoInit = false,
            initialState = initialState,
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl() },
            reducer = ReducerImpl()
        ) {}
    }

    sealed interface Action {
        object Start : Action
        data class Feedback(val feedback: PlayerFeedback) : Action
    }

    sealed interface Msg {
        object StartShlokaConsumed : Msg
        object Play : Msg
        object Pause : Msg
        object ForcePause : Msg
        object Idle : Msg
        object NoAudio : Msg
        data class ResetCounter(val durationMs: Long) : Msg
        data class NextRepeat(val currentRepeat: Int, val durationMs: Long) : Msg
        data class Update(
            val index: Int,
            val title: String,
            val sanskrit: String,
            val words: String,
            val translation: String,
        ) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Start)
            }

            scope.launch {
                deps.playerBus.observeFeedback({ dispatch(Feedback(it)) }, scope)
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<PlayerIntent, Action, PlayerState, Msg, PlayerLabel>() {

        private var taskIndex = 0
        private var playJob: Job? = null
        private var currentPlayTask: Task? = null

        override fun executeAction(action: Action, getState: () -> PlayerState) {
            when (action) {
                Start -> start(getState())
                is Feedback -> feedback(action.feedback)
            }
        }

        override fun executeIntent(intent: PlayerIntent, getState: () -> PlayerState) {
            when (intent) {
                ForcePlay -> forcePlay()
                ForcePause -> forcePause()
                Stop -> stop()
                Prev -> prev(getState())
                Next -> next()
            }
        }

        private fun prev(state: PlayerState) {
            Napier.d("prev", tag = "PlayerStore")
            val index = tasks.firstBefore(taskIndex) { it is SetTrackTask && it.index != state.currentShlokaIndex }
            if (index != -1) {
                taskIndex = index
                nextTask()
            }
        }

        private fun next() {
            Napier.d("next", tag = "PlayerStore")
            val index = tasks.firstAfter(taskIndex) { it is SetTrackTask }
            if (index != -1) {
                taskIndex = index
                nextTask()
            }
        }

        private fun start(state: PlayerState) {
            Napier.d("tasks = $tasks", tag = "TASKS")
            val index = state.startShloka?.shloka?.id?.let { startShlokaId ->
                tasks.indexOfFirst { it is SetTrackTask && it.id == startShlokaId }
            }?.takeIf { it != -1 }
            dispatch(Msg.StartShlokaConsumed)
            taskIndex = index ?: 0
            nextTask()
        }

        private fun nextTask() {
            playJob?.cancel()
            Napier.d("taskIndex = $taskIndex (from ${tasks.lastIndex})", tag = "PlayerStore")
            if (taskIndex <= tasks.lastIndex) {
                Napier.d("nextTask", tag = "PlayerStore")
                scope.launch {
                    handleTask(tasks[taskIndex++])
                }
            }
        }

        private fun feedback(feedback: PlayerFeedback) {
            Napier.d("feedback $feedback", tag = "PlayerStore")
            when (feedback) {
                PlayerFeedback.Ready -> nextTask()
                is PlayerFeedback.Started -> handlePlaybackStarted(feedback)
            }
        }

        private fun handlePlaybackStarted(feedback: PlayerFeedback.Started) {
            playJob = scope.launch(deps.dispatchers.default) {
                delay(feedback.durationMs.speed(audioSpeed))
                nextTask() // next task is Pause or Stop
            }
        }

        suspend fun handleTask(task: Task) {
            when (task) {
                is PlayTask -> play(task)
                is PlayTranslationTask -> playTranslation(task)
                is PauseTask -> pause(task)
                is IdleTask -> idle(task)
                is NoAudioTask -> noAudio(task)
                is SetTrackTask -> setTrack(task)
                is StopTask -> {
                    onPlayCompleted()
                    deps.analytics.playCompleted(
                        count = deps.config.shlokas.size,
                        repeats = deps.config.repeats,
                        durationSec = durationMs / 1000
                    )
                    stop()
                }
                is ResetCounterTask -> resetCounter(task)
            }
        }

        private fun play(task: PlayTask) =
            task.run {
                currentPlayTask = task
                publish(PlayerTask(task))
                dispatch(Msg.Play)
                dispatch(Msg.NextRepeat(currentRepeat, duration.speed(audioSpeed)))
            }

        private fun playTranslation(task: PlayTranslationTask) =
            task.run {
                currentPlayTask = task
                publish(PlayerTask(task))
                dispatch(Msg.Play)
                dispatch(Msg.NextRepeat(currentRepeat, duration.speed(audioSpeed)))
            }

        private fun forcePlay() =
            scope.launch {
                currentPlayTask?.let {
                    handleTask(it)
                } ?: nextTask()
            }

        private fun forcePause() {
            playJob?.cancel()
            publish(PlayerTask(PauseTask(200L)))
            dispatch(Msg.ForcePause)
        }

        private suspend fun pause(task: PauseTask) {
            Napier.d("pause $task", tag = "PlayerStore")
            publish(PlayerTask(task))
            dispatch(Msg.Pause)
            delay(task.duration.speed(audioSpeed))
            nextTask()
        }

        private fun idle(task: IdleTask) {
            if (autoPlay) {
                nextTask()
            } else {
                publish(PlayerTask(task))
                dispatch(Msg.Idle)
            }
        }

        private fun noAudio(task: NoAudioTask) {
            publish(PlayerTask(task))
            dispatch(Msg.NoAudio)
        }

        private fun stop() {
            publish(PlayerTask(StopTask))
            publish(PlayerLabel.Stop)
        }

        private fun setTrack(task: SetTrackTask) =
            task.run {
                currentPlayTask = null
                val title = resolveTitle(id)

                if (hasAudio) {
                    publish(
                        PlayerTask(
                            copy(
                                title = title,
                                description = resolveDescription(id),
                            )
                        )
                    )
                }
                dispatch(
                    Msg.Update(
                        index = index,
                        title = title,
                        sanskrit = resolveSanskrit(id),
                        words = resolveWords(id),
                        translation = resolveTranslation(id),
                    )
                )
            }

        private fun resetCounter(task: ResetCounterTask) {
            dispatch(Msg.ResetCounter(task.duration.speed(audioSpeed)))
            nextTask()
        }
    }

    private inner class ReducerImpl : Reducer<PlayerState, Msg> {
        override fun PlayerState.reduce(msg: Msg): PlayerState =
            when (msg) {
                Msg.StartShlokaConsumed -> copy(startShloka = null)
                Msg.Play -> copy(playbackState = PLAYING)
                Msg.Pause -> copy(playbackState = PAUSED)
                Msg.ForcePause -> copy(playbackState = FORCE_PAUSED)
                Msg.Idle -> copy(playbackState = IDLE)
                Msg.NoAudio -> copy(playbackState = NO_AUDIO)
                is Msg.NextRepeat -> copy(
                    currentRepeat = msg.currentRepeat,
                    durationMs = msg.durationMs
                )
                is Msg.ResetCounter -> copy(
                    durationMs = msg.durationMs,
                    currentRepeat = 0,
                )
                is Msg.Update -> copy(
                    title = msg.title,
                    sanskrit = msg.sanskrit,
                    words = msg.words,
                    translation = msg.translation,
                    currentShlokaIndex = msg.index,
                    playbackState = IDLE,
                )
            }
    }
}

package com.a_blekot.shlokas.common.player_impl.store

import com.a_blekot.shlokas.common.data.FtueStatus
import com.a_blekot.shlokas.common.data.FtueStatus.SHOW_ARROW
import com.a_blekot.shlokas.common.data.createTasks
import com.a_blekot.shlokas.common.data.durationMs
import com.a_blekot.shlokas.common.data.tasks.*
import com.a_blekot.shlokas.common.player_api.PlaybackState
import com.a_blekot.shlokas.common.player_api.PlaybackState.*
import com.a_blekot.shlokas.common.player_api.PlayerFeedback
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.player_impl.PlayerDeps
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.*
import com.a_blekot.shlokas.common.player_impl.store.PlayerLabel.PlayerTask
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory.Action.Feedback
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory.Action.Start
import com.a_blekot.shlokas.common.utils.getAppLaunchCount
import com.a_blekot.shlokas.common.utils.getAutoPlay
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
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
) : StringResourceHandler by deps.stringResourceHandler {

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
            title = resolveTitle(firstShloka.id),
            sanskrit = resolveSanskrit(firstShloka.id),
            words = resolveWords(firstShloka.id),
            translation = resolveTranslation(firstShloka.id),
            durationMs = durationMs,
            totalRepeats = deps.config.repeats,
            totalShlokasCount = deps.config.shlokas.filter { it.isSelected }.size,
            totalDurationMs = deps.config.durationMs,
            showPointingArrow = getAppLaunchCount() <= SHOW_ARROW.appLaunches,
            isAutoplay = getAutoPlay()
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
        object Idle: Msg
        data class ResetCounter(val durationMs: Long): Msg
        data class NextRepeat(val timeMs: Long, val currentRepeat: Int, val durationMs: Long) : Msg
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
                Play -> nextTask()
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
                is PlayerFeedback.Started -> handlePlaybackStarted(feedback.durationMs)
//                PlayerFeedback.Completed -> nextTask()
            }
        }

        private fun handlePlaybackStarted(durationMs: Long) {
            scope.launch(deps.dispatchers.default) {
                delay(durationMs)
                nextTask() // next task is Pause or Stop
            }
        }

        suspend fun handleTask(task: Task) {
            when (task) {
                is PlayTask -> {
                    publish(PlayerTask(task))
                    dispatch(Msg.Play)
                    task.run {
                        dispatch(Msg.NextRepeat(absoluteStartMs, currentRepeat, duration))
                    }
                }
                is PauseTask -> pause(task)
                is IdleTask -> idle(task)
                is SetTrackTask -> setTrack(task)
                is StopTask -> stop()
                is ResetCounterTask -> resetCounter(task)
            }
        }

        private suspend fun pause(task: PauseTask) {
            publish(PlayerTask(task))
            dispatch(Msg.Pause)
            delay(task.duration)
            nextTask()
        }

        private fun idle(task: IdleTask) {
            if (getAutoPlay()) {
                nextTask()
            } else {
                publish(PlayerTask(task))
                dispatch(Msg.Idle)
            }
        }

        private fun stop() {
            publish(PlayerTask(StopTask))
            publish(PlayerLabel.Stop)
        }

        private fun setTrack(task: SetTrackTask) =
            task.run {
                val title = resolveTitle(id)

                publish(
                    PlayerTask(
                        copy(
                            title = title,
                            description = resolveDescription(id),
                        )
                    )
                )
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
            dispatch(Msg.ResetCounter(task.duration))
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
                Msg.Play -> copy(playbackState = PLAYING)
                Msg.Pause -> copy(playbackState = PAUSED)
                Msg.Idle -> copy(playbackState = IDLE)
                is Msg.NextRepeat -> copy(
                    timeMs = msg.timeMs,
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
                )
            }
    }
}

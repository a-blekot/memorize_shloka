package com.a_blekot.shlokas.common.player_impl

import com.a_blekot.shlokas.common.data.RepeatMode
import com.a_blekot.shlokas.common.utils.Consumer
import com.a_blekot.shlokas.common.utils.asValue
import com.a_blekot.shlokas.common.utils.init
import com.a_blekot.shlokas.common.utils.lifecycleCoroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.a_blekot.shlokas.common.data.createTasks
import com.a_blekot.shlokas.common.data.speed
import com.a_blekot.shlokas.common.data.tasks.PlayTranslationTask
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerOutput
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.*
import com.a_blekot.shlokas.common.player_impl.store.PlayerLabel
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory
import com.a_blekot.shlokas.common.utils.audioSpeed
import com.a_blekot.shlokas.common.utils.autoPlay
import com.a_blekot.shlokas.common.utils.locale
import com.a_blekot.shlokas.common.utils.repeatMode
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
import com.a_blekot.shlokas.common.utils.toVerseName
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val KEY_PLAYER_STATE = "KEY_PLAYER_STATE"

class PlayerComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val deps: PlayerDeps,
    private val output: Consumer<PlayerOutput>
) : PlayerComponent, ComponentContext by componentContext, StringResourceHandler by deps.stringResourceHandler {

    private val tasks = deps.config.createTasks().map {
        if (it is PlayTranslationTask) {
            it.copy(text = "${it.id.toVerseName(locale)}. " + resolveTranslation(it.id))
        } else {
            it
        }
    }
    private val durationMs = tasks.sumOf { it.duration }.speed(audioSpeed)
    private val startShloka = deps.config.startShloka?.shloka ?: deps.config.shlokas.first().shloka

    private val initialState =
        PlayerState(
            repeatMode = repeatMode,
            title = resolveTitle(startShloka.id),
            sanskrit = resolveSanskrit(startShloka.id),
            words = resolveWords(startShloka.id),
            translation = resolveTranslation(startShloka.id),
            durationMs = durationMs,
            totalRepeats = deps.config.repeats,
            totalShlokasCount = deps.config.shlokas.filter { it.isSelected }.size,
            totalDurationMs = durationMs,
            isAutoplay = autoPlay,
            startShloka = deps.config.startShloka,
        )

    private val store =
        instanceKeeper.getStore {
            PlayerStoreFactory(
                storeFactory = storeFactory,
                tasks = tasks,
                initialState = stateKeeper.consume(KEY_PLAYER_STATE, PlayerState.serializer()) ?: initialState,
                deps = deps,
            ).create()
        }

    private val scope: CoroutineScope = lifecycleCoroutineScope(deps.dispatchers.main)

    override val flow: Value<PlayerState> = store.asValue()

    init {
        store.labels
            .onEach(::handleLabel)
            .launchIn(scope)

        Napier.d("PlayerComponentImpl init", tag = "PlayerStore")
        store.init(instanceKeeper)
        stateKeeper.register(KEY_PLAYER_STATE, PlayerState.serializer()) { store.state }
    }

    override fun forcePlay() = store.accept(ForcePlay)
    override fun forcePause() = store.accept(ForcePause)
    override fun stop() = store.accept(Stop)

    override fun prev() = store.accept(Prev)
    override fun next() = store.accept(Next)
    override fun repeatModeChanged(newMode: RepeatMode) = store.accept(RepeatModeChanged(newMode))

    private fun handleLabel(label: PlayerLabel) {
        when (label) {
            PlayerLabel.Stop -> { output(PlayerOutput.Stop) }
            is PlayerLabel.PlayerTask -> { deps.playerBus.update(label.task) }
        }
    }
}
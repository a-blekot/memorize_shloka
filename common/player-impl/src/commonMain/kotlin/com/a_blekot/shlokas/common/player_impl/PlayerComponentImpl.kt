package com.a_blekot.shlokas.common.player_impl

import com.a_blekot.shlokas.common.data.createTasks
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerOutput
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.ForcePause
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.ForcePlay
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Restart
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Stop
import com.a_blekot.shlokas.common.player_impl.store.PlayerLabel
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory
import com.a_blekot.shlokas.common.utils.*
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler
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
            totalDurationMs = durationMs,
            isAutoplay = getAutoPlay()
        )

    private val store =
        instanceKeeper.getStore {
            PlayerStoreFactory(
                storeFactory = storeFactory,
                tasks = tasks,
                durationMs= durationMs,
                initialState = stateKeeper.consume(KEY_PLAYER_STATE) ?: initialState,
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
        stateKeeper.register(KEY_PLAYER_STATE) { store.state }
    }

    override fun forcePlay() = store.accept(ForcePlay)
    override fun forcePause() = store.accept(ForcePause)
    override fun restart() = store.accept(Restart)
    override fun stop() = store.accept(Stop)

    private fun handleLabel(label: PlayerLabel) {
        when (label) {
            PlayerLabel.Stop -> { output(PlayerOutput.Stop) }
            is PlayerLabel.PlayerTask -> { deps.playerBus.update(label.task) }
        }
    }
}
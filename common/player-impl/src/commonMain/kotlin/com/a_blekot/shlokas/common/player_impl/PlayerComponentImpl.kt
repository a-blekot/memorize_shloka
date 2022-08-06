package com.a_blekot.shlokas.common.player_impl

import com.a_blekot.shlokas.common.data.tasks.StopTask
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerOutput
import com.a_blekot.shlokas.common.player_api.PlayerState
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Pause
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Play
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Restart
import com.a_blekot.shlokas.common.player_impl.store.PlayerIntent.Stop
import com.a_blekot.shlokas.common.player_impl.store.PlayerLabel
import com.a_blekot.shlokas.common.player_impl.store.PlayerStoreFactory
import com.a_blekot.shlokas.common.utils.Consumer
import com.a_blekot.shlokas.common.utils.asValue
import com.a_blekot.shlokas.common.utils.getStore
import com.a_blekot.shlokas.common.utils.lifecycleCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnStop
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PlayerComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val deps: PlayerDeps,
    private val output: Consumer<PlayerOutput>
) : PlayerComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            PlayerStoreFactory(
                storeFactory = storeFactory,
                deps = deps,
            ).create()
        }

    private val scope: CoroutineScope = lifecycleCoroutineScope(deps.dispatchers.main)

    override val flow: Value<PlayerState> = store.asValue()

    init {
        Napier.d("playerBus = ${deps.playerBus}", tag="PlayerBus")

        store.labels
            .onEach(::handleLabel)
            .launchIn(scope)

        lifecycle.doOnPause {
            store.accept(Pause)
        }
        lifecycle.doOnDestroy {
            Napier.d("playerBus = ${deps.playerBus}", tag="PlayerBus")

            deps.playerBus.update(StopTask)
        }

        store.init()
    }

    override fun play() = store.accept(Play)
    override fun pause() = store.accept(Pause)
    override fun restart() = store.accept(Restart)
    override fun stop() = store.accept(Stop)

    private fun handleLabel(label: PlayerLabel) {
        when (label) {
            PlayerLabel.Stop -> { output(PlayerOutput.Stop) }
            is PlayerLabel.PlayerTask -> { deps.playerBus.update(label.task) }
        }
    }
}
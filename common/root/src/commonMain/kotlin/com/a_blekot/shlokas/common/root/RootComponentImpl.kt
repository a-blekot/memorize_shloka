package com.a_blekot.shlokas.common.root

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.data.PlayConfig
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.a_blekot.shlokas.common.details_api.DetailsOutput
import com.a_blekot.shlokas.common.details_impl.DetailsComponentImpl
import com.a_blekot.shlokas.common.details_impl.DetailsDeps
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.a_blekot.shlokas.common.list_api.ListOutput
import com.a_blekot.shlokas.common.list_impl.ListComponentImpl
import com.a_blekot.shlokas.common.list_impl.ListDeps
import com.a_blekot.shlokas.common.player_api.PlayerComponent
import com.a_blekot.shlokas.common.player_api.PlayerOutput
import com.a_blekot.shlokas.common.player_impl.PlayerComponentImpl
import com.a_blekot.shlokas.common.player_impl.PlayerDeps
import com.a_blekot.shlokas.common.root.RootComponent.Child.Details
import com.a_blekot.shlokas.common.root.RootComponent.Child.List
import com.a_blekot.shlokas.common.root.RootComponent.Child.Player
import com.a_blekot.shlokas.common.utils.Consumer
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory

class RootComponentImpl internal constructor(
    componentContext: ComponentContext,
    private val list: (ComponentContext, Consumer<ListOutput>) -> ListComponent,
    private val player: (ComponentContext, config: PlayConfig, Consumer<PlayerOutput>) -> PlayerComponent,
    private val details: (ComponentContext, config: ShlokaConfig, Consumer<DetailsOutput>) -> DetailsComponent,
) : RootComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        deps: RootDeps,
    ) : this(
        componentContext = componentContext,
        list = { childContext, output ->
            ListComponentImpl(
                componentContext = childContext,
                storeFactory = storeFactory,
                deps = deps.run { ListDeps(ListConfig(), filer, dispatchers) },
                output = output
            )
        },
        player = { childContext, config, output ->
            PlayerComponentImpl(
                componentContext = childContext,
                storeFactory = storeFactory,
                deps = deps.run { PlayerDeps(config, playerBus, dispatchers) },
                output = output
            )
        },
        details = { childContext, config, output ->
            DetailsComponentImpl(
                componentContext = childContext,
                storeFactory = storeFactory,
                deps = deps.run { DetailsDeps(config, dispatchers) },
                output = output
            )
        },
    )

    private val router =
        router<Configuration, RootComponent.Child>(
            initialConfiguration = Configuration.List,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val routerState: Value<RouterState<*, RootComponent.Child>> = router.state

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): RootComponent.Child =
        when (configuration) {
            is Configuration.List ->
                List(list(componentContext, Consumer(::onListOutput)))
            is Configuration.Player ->
                Player(player(componentContext, configuration.config, Consumer(::onPlayerOutput)))
            is Configuration.Details ->
                Details(details(componentContext, configuration.config, Consumer(::onDetailsOutput)))
        }

    private fun onListOutput(output: ListOutput): Unit =
        when (output) {
            is ListOutput.Play -> router.push(Configuration.Player(output.config))
            is ListOutput.Details -> router.push(Configuration.Details(output.config))
        }

    private fun onPlayerOutput(output: PlayerOutput): Unit =
        when (output) {
            is PlayerOutput.Stop -> router.pop()
        }

    private fun onDetailsOutput(output: DetailsOutput): Unit =
        when (output) {
            is DetailsOutput.SaveConfig -> router.pop { isSuccess ->
                if (isSuccess) {
                    (router.activeChild.instance as? List)?.component?.saveShloka(output.config)
                }
            }
        }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object List : Configuration()

        @Parcelize
        data class Player(val config: PlayConfig) : Configuration()

        @Parcelize
        data class Details(val config: ShlokaConfig) : Configuration()
    }
}
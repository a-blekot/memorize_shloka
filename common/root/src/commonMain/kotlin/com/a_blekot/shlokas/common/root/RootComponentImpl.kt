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
import com.a_blekot.shlokas.common.root.RootComponent.Child.*
import com.a_blekot.shlokas.common.settings_api.SettingsComponent
import com.a_blekot.shlokas.common.settings_api.SettingsOutput
import com.a_blekot.shlokas.common.settings_impl.SettingsComponentImpl
import com.a_blekot.shlokas.common.settings_impl.SettingsDeps
import com.a_blekot.shlokas.common.utils.Consumer
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.listentoprabhupada.common.donations_api.DonationsComponent
import com.listentoprabhupada.common.donations_api.DonationsOutput
import com.listentoprabhupada.common.donations_impl.DonationsComponentImpl
import com.listentoprabhupada.common.donations_impl.DonationsDeps

class RootComponentImpl internal constructor(
    componentContext: ComponentContext,
    private val deps: RootDeps,
    private val list: (ComponentContext, Consumer<ListOutput>) -> ListComponent,
    private val player: (ComponentContext, config: PlayConfig, Consumer<PlayerOutput>) -> PlayerComponent,
    private val details: (ComponentContext, config: ShlokaConfig, Consumer<DetailsOutput>) -> DetailsComponent,
    private val settings: (ComponentContext, Consumer<SettingsOutput>) -> SettingsComponent,
    private val donations: (ComponentContext, Consumer<DonationsOutput>) -> DonationsComponent,
) : RootComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        deps: RootDeps,
    ) : this(
        componentContext = componentContext,
        deps = deps,
        list = { childContext, output ->
            ListComponentImpl(
                componentContext = childContext,
                storeFactory = storeFactory,
                deps = deps.run { ListDeps(ListConfig(), filer, configReader, stringResourceHandler, analytics, dispatchers) },
                output = output
            )
        },
        player = { childContext, config, output ->
            PlayerComponentImpl(
                componentContext = childContext,
                storeFactory = storeFactory,
                deps = deps.run { PlayerDeps(config, playerBus, stringResourceHandler, analytics, dispatchers) },
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
        settings = { childContext, output ->
            SettingsComponentImpl(
                componentContext = childContext,
                storeFactory = storeFactory,
                deps = deps.run { SettingsDeps(analytics, dispatchers) },
                output = output
            )
        },
        donations = { childContext, output ->
            DonationsComponentImpl(
                componentContext = childContext,
                storeFactory = storeFactory,
                deps = deps.run { DonationsDeps(analytics, billingHelper, dispatchers, stringResourceHandler) },
                output = output
            )
        },
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack =
        childStack(
            source = navigation,
            initialConfiguration = Configuration.List,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, RootComponent.Child>>
        get() = stack

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): RootComponent.Child =
        when (configuration) {
            is Configuration.List ->
                List(list(componentContext, Consumer(::onListOutput)))
            is Configuration.Player ->
                Player(player(componentContext, configuration.config, Consumer(::onPlayerOutput)))
            is Configuration.Details ->
                Details(details(componentContext, configuration.config, Consumer(::onDetailsOutput)))
            is Configuration.Settings ->
                Settings(settings(componentContext, Consumer(::onSettingsOutput)))
            is Configuration.Donations ->
                Donations(donations(componentContext, Consumer(::onDonationsOutput)))
        }

    private fun onListOutput(output: ListOutput): Unit =
        when (output) {
            is ListOutput.Play -> navigation.push(Configuration.Player(output.config))
            is ListOutput.Details -> navigation.push(Configuration.Details(output.config))
            is ListOutput.Settings -> navigation.push(Configuration.Settings)
            is ListOutput.Donations -> navigation.push(Configuration.Donations)
            is ListOutput.ShareApp -> deps.onShareApp()
            is ListOutput.InappReview -> deps.onInappReview()
        }

    private fun onPlayerOutput(output: PlayerOutput): Unit =
        when (output) {
            is PlayerOutput.Stop -> navigation.pop()
        }

    private fun onDetailsOutput(output: DetailsOutput): Unit =
        when (output) {
            is DetailsOutput.Play -> navigation.push(Configuration.Player(output.config))
            is DetailsOutput.SaveConfig -> navigation.pop { isSuccess ->
                if (isSuccess) {
                    (childStack.value.active.instance as? RootComponent.Child.List)?.component?.saveShloka(output.config)
                }
            }
        }

    private fun onSettingsOutput(output: SettingsOutput) {
        when (output) {
            SettingsOutput.Email -> deps.onEmail()
            SettingsOutput.Donations -> navigation.push(Configuration.Donations)
        }
    }

    private fun onDonationsOutput(output: DonationsOutput) {
        when (output) {
            DonationsOutput.SuccessPurchase -> navigation.pop()
        }
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object List : Configuration()

        @Parcelize
        data class Player(val config: PlayConfig) : Configuration()

        @Parcelize
        data class Details(val config: ShlokaConfig) : Configuration()

        @Parcelize
        object Settings : Configuration()

        @Parcelize
        object Donations : Configuration()
    }
}
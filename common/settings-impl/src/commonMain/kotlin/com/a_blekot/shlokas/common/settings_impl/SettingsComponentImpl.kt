package com.a_blekot.shlokas.common.settings_impl

import com.a_blekot.shlokas.common.settings_api.SettingsComponent
import com.a_blekot.shlokas.common.settings_api.SettingsOutput
import com.a_blekot.shlokas.common.settings_api.SettingsState
import com.a_blekot.shlokas.common.settings_impl.store.SettingsIntent.*
import com.a_blekot.shlokas.common.settings_impl.store.SettingsStoreFactory
import com.a_blekot.shlokas.common.utils.*
import com.a_blekot.shlokas.common.utils.analytics.AnalyticsScreen
import com.a_blekot.shlokas.common.utils.analytics.tutorialComplete
import com.a_blekot.shlokas.common.utils.analytics.tutorialSettings
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.CoroutineScope

class SettingsComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val deps: SettingsDeps,
    private val output: Consumer<SettingsOutput>
) : SettingsComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            SettingsStoreFactory(
                storeFactory = storeFactory,
                deps = deps,
            ).create()
        }

    private val scope: CoroutineScope = lifecycleCoroutineScope(deps.dispatchers.main)

    override val flow: Value<SettingsState> = store.asValue()

    init {
//        store.labels
//            .onEach(::handleLabel)
//            .launchIn(scope)

        store.init()
    }

    override fun setRepeats(value: Int) = store.accept(Repeats(value))
    override fun setPause(value: Long) = store.accept(Pause(value))
    override fun setWeek(value: Int) = store.accept(Weeks(value))
    override fun setLocale(value: String) = store.accept(Locale(value))
    override fun setAutoplay(value: Boolean) = store.accept(Autoplay(value))
    override fun onShowTutorial() = deps.analytics.tutorialSettings()
    override fun onTutorialCompleted() {
        if (!isTutorialCompleted()) {
            deps.analytics.tutorialComplete(AnalyticsScreen.SETTINGS)
            setTutorialCompleted()
        }
    }
    override fun sendEmail() = output(SettingsOutput.Email)
    override fun donations() = output(SettingsOutput.Donations)
}
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

private const val KEY_SETTINGS_STATE = "KEY_SETTINGS_STATE"

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
                initialState = stateKeeper.consume(KEY_SETTINGS_STATE) ?: initialState,
            ).create()
        }

    private val initialState
        get() = SettingsState(
            repeats = repeats,
            pause = pause,
            week = currentWeek,
            locale = locale,
            isAutoplay = autoPlay,
            withSanskrit = withSanskrit,
            withTranslation = withTranslation,
            showClosePlayerDialog = showClosePlayerDialog
        )

    override val flow: Value<SettingsState> = store.asValue()

    init {
        store.init(instanceKeeper)
        stateKeeper.register(KEY_SETTINGS_STATE) { store.state }
    }

    override fun setRepeats(value: Int) = store.accept(Repeats(value))
    override fun setPause(value: Long) = store.accept(Pause(value))
    override fun setWeek(value: Int) = store.accept(Weeks(value))
    override fun setLocale(value: String) = store.accept(Locale(value))
    override fun setAutoplay(value: Boolean) = store.accept(Autoplay(value))
    override fun setWithSanskrit(value: Boolean) = store.accept(WithSanskrit(value))
    override fun setWithTranslation(value: Boolean) = store.accept(WithTranslation(value))
    override fun setShowClosePlayerDialog(value: Boolean) = store.accept(ShowClosePlayerDialog(value))
    override fun onShowTutorial() = deps.analytics.tutorialSettings()
    override fun onTutorialCompleted() {
        if (!isTutorialCompleted) {
            deps.analytics.tutorialComplete(AnalyticsScreen.SETTINGS)
            isTutorialCompleted = true
        }
    }
    override fun sendEmail() = deps.platformApi.onEmail()
    override fun shareApp() = deps.platformApi.onShareApp()
    override fun rateUs() = deps.platformApi.onRateUs()
    override fun selectTtsVoice() {
        if (deps.platformApi.hasTts) {
            deps.platformApi.onSelectTtsVoice()
        }
    }
    override fun donations() = output(SettingsOutput.Donations)
    override fun back() = output(SettingsOutput.Back)
}
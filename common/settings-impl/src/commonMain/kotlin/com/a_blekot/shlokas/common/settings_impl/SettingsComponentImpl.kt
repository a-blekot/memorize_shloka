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
            getRepeats(),
            getPause(),
            getCurrentWeek(),
            getLocale(),
            getAutoPlay(),
            withTranslation(),
            showClosePlayerDialog()
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
    override fun setWithTranslation(value: Boolean) = store.accept(WithTranslation(value))
    override fun setShowClosePlayerDialog(value: Boolean) = store.accept(ShowClosePlayerDialog(value))
    override fun onShowTutorial() = deps.analytics.tutorialSettings()
    override fun onTutorialCompleted() {
        if (!isTutorialCompleted()) {
            deps.analytics.tutorialComplete(AnalyticsScreen.SETTINGS)
            setTutorialCompleted()
        }
    }
    override fun sendEmail() = output(SettingsOutput.Email)
    override fun shareApp() = output(SettingsOutput.ShareApp)
    override fun rateUs() = output(SettingsOutput.RateUs)
    override fun selectTtsVoice() = output(SettingsOutput.SelectTtsVoice)
    override fun donations() = output(SettingsOutput.Donations)
    override fun back() = output(SettingsOutput.Back)
}
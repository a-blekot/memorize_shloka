package com.a_blekot.shlokas.common.settings_impl

import com.a_blekot.shlokas.common.settings_api.SettingsComponent
import com.a_blekot.shlokas.common.settings_api.SettingsOutput
import com.a_blekot.shlokas.common.settings_api.SettingsState
import com.a_blekot.shlokas.common.settings_impl.store.SettingsIntent
import com.a_blekot.shlokas.common.settings_impl.store.SettingsIntent.*
import com.a_blekot.shlokas.common.settings_impl.store.SettingsLabel
import com.a_blekot.shlokas.common.settings_impl.store.SettingsStoreFactory
import com.a_blekot.shlokas.common.utils.*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
    override fun setWeek(value: Int) = store.accept(Weeks(value))
    override fun setLocale(value: String) = store.accept(Locale(value))
    override fun setAutoplay(value: Boolean) = store.accept(Autoplay(value))
    override fun onTutorialCompleted() = setTutorialCompleted()
    override fun sendEmail() = output(SettingsOutput.Email)
}
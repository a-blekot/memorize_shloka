package com.a_blekot.shlokas.common.list_impl

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.data.PlayConfig
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.a_blekot.shlokas.common.list_api.ListOutput
import com.a_blekot.shlokas.common.list_api.ListState
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.*
import com.a_blekot.shlokas.common.list_impl.store.ListLabel
import com.a_blekot.shlokas.common.list_impl.store.ListStoreFactory
import com.a_blekot.shlokas.common.utils.*
import com.a_blekot.shlokas.common.utils.analytics.playList
import com.a_blekot.shlokas.common.utils.analytics.playShloka
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnStart
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ListComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val deps: ListDeps,
    private val output: Consumer<ListOutput>
) : ListComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            ListStoreFactory(
                storeFactory = storeFactory,
                deps = deps,
            ).create()
        }

    private val scope: CoroutineScope = lifecycleCoroutineScope(deps.dispatchers.main)

    override val flow: Value<ListState> = store.asValue()

    init {

        store.labels
            .onEach(::handleLabel)
            .launchIn(scope)

        lifecycle.doOnStart {
            Napier.d("list doOnStart")
            store.accept(CheckLocale)
            store.accept(CheckPreRating)
        }

        store.init()
    }

    override fun add() = store.accept(Add)
    override fun save() = store.accept(Save)
    override fun remove(id: String) = store.accept(Remove(id))
    override fun moveUp(id: String) = store.accept(MoveUp(id))
    override fun moveDown(id: String) = store.accept(MoveDown(id))
    override fun select(id: String, isSelected: Boolean) = store.accept(Select(id, isSelected))
    override fun details(config: ShlokaConfig) = output(ListOutput.Details(config))
    override fun play() = onPlay()
    override fun play(config: ShlokaConfig) {
        val playConfig = config.toPlayConfig()
        playShlokaAnalytics(config.shloka.id, playConfig)
        output(ListOutput.Play(playConfig))
    }

    override fun settings() = output(ListOutput.Settings)
    override fun shareApp() = output(ListOutput.ShareApp)
    override fun saveShloka(config: ShlokaConfig) = store.accept(SaveShloka(config))
    override fun onTutorialCompleted() = store.accept(TutorialCompleted)
    override fun onTutorialSkipped() = store.accept(TutorialSkipped)
    override fun setList(id: String) = store.accept(SetList(id))

    override fun onPreRatingAccepted() = store.accept(PreRatingAccepted)
    override fun onPreRatingClosed() = store.accept(PreRatingClosed)

    private fun handleLabel(label: ListLabel) {
        when (label) {
            ListLabel.ShowInappReview -> { output(ListOutput.InappReview) }
        }
    }

    private fun onPlay() =
        store.state.config.run {
            val playConfig = this.toPlayConfig()
            if (playConfig.shlokas.isNotEmpty()) {
                playListAnalytics(id, playConfig)
                output(ListOutput.Play(playConfig))
            }
        }

    private fun ListConfig.toPlayConfig() =
        PlayConfig(
            week = getCurrentWeek(),
            shlokas = list.filter { it.isSelected && it.shloka.hasAudio },
            repeats = getRepeats(),
            pauseAfterEach = getPause()
        )

    private fun ShlokaConfig.toPlayConfig() =
        PlayConfig(
            week = getCurrentWeek(),
            shlokas = listOf(copy(isSelected = true)),
            repeats = getRepeats(),
            pauseAfterEach = getPause(),
        )

    private fun playListAnalytics(id: String, config: PlayConfig) =
        deps.analytics.playList(
            listId = id,
            count = config.shlokas.size,
            repeats = config.repeats
        )

    private fun playShlokaAnalytics(id: String, config: PlayConfig) =
        deps.analytics.playShloka(
            shlokaId = id,
            repeats = config.repeats
        )
}
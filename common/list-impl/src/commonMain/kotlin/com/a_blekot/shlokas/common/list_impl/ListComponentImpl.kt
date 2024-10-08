package com.a_blekot.shlokas.common.list_impl

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.PlayConfig
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.data.ShlokaId
import com.a_blekot.shlokas.common.data.YOU_TUBE_SHLOKA_SMARANAM
import com.a_blekot.shlokas.common.list_api.ListComponent
import com.a_blekot.shlokas.common.list_api.ListOutput
import com.a_blekot.shlokas.common.list_api.ListPresentation
import com.a_blekot.shlokas.common.list_api.ListState
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Add
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.CheckLocale
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.CheckPreRating
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.MoveDown
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.MoveUp
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.PreRatingAccepted
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.PreRatingClosed
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Remove
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Save
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.SaveShloka
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.Select
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.SetList
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.TutorialCompleted
import com.a_blekot.shlokas.common.list_impl.store.ListIntent.TutorialSkipped
import com.a_blekot.shlokas.common.list_impl.store.ListLabel
import com.a_blekot.shlokas.common.list_impl.store.ListStoreFactory
import com.a_blekot.shlokas.common.utils.Consumer
import com.a_blekot.shlokas.common.utils.analytics.playList
import com.a_blekot.shlokas.common.utils.analytics.playShloka
import com.a_blekot.shlokas.common.utils.asValue
import com.a_blekot.shlokas.common.utils.init
import com.a_blekot.shlokas.common.utils.lifecycleCoroutineScope
import com.a_blekot.shlokas.common.utils.locale
import com.a_blekot.shlokas.common.utils.pause
import com.a_blekot.shlokas.common.utils.repeatMode
import com.a_blekot.shlokas.common.utils.repeats
import com.a_blekot.shlokas.common.utils.withSanskrit
import com.a_blekot.shlokas.common.utils.withTranslation
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnStart
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val KEY_LIST_STATE = "KEY_LIST_STATE"

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
                initialState = stateKeeper.consume(KEY_LIST_STATE, strategy = ListState.serializer()) ?: initialState(),
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

        store.init(instanceKeeper)
        stateKeeper.register(KEY_LIST_STATE, strategy = ListState.serializer()) { store.state }
    }

    private fun initialState() =
        ListState(
            deps.config,
            availableLists = availableLists(),
            locale = locale,
            shouldShowTutorial = false
        )

    override fun resolveDescription(id: ShlokaId): String =
        deps.stringResourceHandler.resolveDescription(id)

    override fun add() = store.accept(Add)
    override fun save() = store.accept(Save)
    override fun remove(id: ShlokaId) = store.accept(Remove(id))
    override fun moveUp(id: ShlokaId) = store.accept(MoveUp(id))
    override fun moveDown(id: ShlokaId) = store.accept(MoveDown(id))
    override fun select(id: ShlokaId, isSelected: Boolean) = store.accept(Select(id, isSelected))
    override fun details(config: ShlokaConfig) = output(ListOutput.Details(config))
    override fun play() = onPlay()
    override fun play(config: ShlokaConfig) = onPlay(config)

    override fun youtube() = deps.platformApi.onLink(YOU_TUBE_SHLOKA_SMARANAM)
    override fun settings() = output(ListOutput.Settings)
    override fun donations() = output(ListOutput.Donations)
    override fun shareApp() = deps.platformApi.onShareApp()
    override fun saveShloka(config: ShlokaConfig) = store.accept(SaveShloka(config))
    override fun onTutorialCompleted() = store.accept(TutorialCompleted)
    override fun onTutorialSkipped() = store.accept(TutorialSkipped)
    override fun setList(type: ListId) = store.accept(SetList(type))

    override fun onPreRatingAccepted() = store.accept(PreRatingAccepted)
    override fun onPreRatingClosed() = store.accept(PreRatingClosed)

    private fun handleLabel(label: ListLabel) {
        when (label) {
            ListLabel.ShowInappReview -> {
                if (deps.platformApi.hasInappReview) {
                    deps.platformApi.onInappReview()
                }
            }
        }
    }

    private fun onPlay(startShloka: ShlokaConfig? = null) =
        store.state.config.run {
            val playConfig = this.toPlayConfig(startShloka)
            if (playConfig.shlokas.isNotEmpty()) {
                startShloka?.let {
                    playShlokaAnalytics(startShloka.shloka.id.id, playConfig)
                }
                playListAnalytics(id.id, playConfig)
                output(ListOutput.Play(playConfig))
            }
        }

    private fun ListConfig.toPlayConfig(startShloka: ShlokaConfig? = null) =
        list
            .map {
                if (it.shloka.id == startShloka?.shloka?.id) {
                    it.copy(isSelected = true)
                } else {
                    it
                }
            }
            .filter { it.isSelected || it.shloka.id == startShloka?.shloka?.id }
            .let { shlokas ->
                PlayConfig(
                    repeatMode = repeatMode,
                    shlokas = shlokas,
                    startShloka = startShloka,
                    repeats = repeats,
                    withSanskrit = withSanskrit,
                    withTranslation = withTranslation,
                    pauseAfterEach = pause,
                )
            }

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

    private fun availableLists() =
        ListId.entries.map {
            ListPresentation(
                type = it,
                title = deps.stringResourceHandler.resolveListShortTitle(it),
                isSelected = deps.config.id == it
            )
        }
}
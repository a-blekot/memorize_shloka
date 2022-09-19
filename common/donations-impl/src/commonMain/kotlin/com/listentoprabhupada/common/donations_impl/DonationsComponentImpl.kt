package com.listentoprabhupada.common.donations_impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.listentoprabhupada.common.donations_api.DonationsComponent
import com.listentoprabhupada.common.donations_api.DonationsOutput
import com.listentoprabhupada.common.donations_api.DonationsState
import com.listentoprabhupada.common.donations_impl.store.DonationsIntent.*
import com.listentoprabhupada.common.donations_impl.store.DonationsStoreFactory
import com.prabhupadalectures.common.utils.Consumer
import com.prabhupadalectures.common.utils.asValue
import com.prabhupadalectures.common.utils.getStore


class DonationsComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    deps: DonationsDeps,
    private val output: Consumer<DonationsOutput>
) : DonationsComponent, ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            DonationsStoreFactory(
                storeFactory = storeFactory,
                deps = deps,
            ).create()
        }

    override val flow: Value<DonationsState> = store.asValue()

    override fun onNext() = store.accept(Next)
    override fun onPrev() = store.accept(Prev)
}

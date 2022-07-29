package com.a_blekot.shlokas.common.details_impl.store

import com.a_blekot.shlokas.common.details_api.DetailsState
import com.arkivanov.mvikotlin.core.store.Store

internal interface DetailsStore : Store<DetailsIntent, DetailsState, Nothing>

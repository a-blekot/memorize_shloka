package com.a_blekot.shlokas.common.list_impl.store

import com.a_blekot.shlokas.common.list_api.ListState
import com.arkivanov.mvikotlin.core.store.Store

internal interface ListStore : Store<ListIntent, ListState, Nothing>

package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.ListConfig

data class ListState(
    val config: ListConfig,
    val availableLists: List<ListPresentation> = emptyList(),
    val locale: String,
    val hasChanges: Boolean = false,
    val shouldShowTutorial: Boolean = true
)

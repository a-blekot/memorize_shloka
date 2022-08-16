package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.ListConfig

data class ListState(
    val config: ListConfig,
    val locale: String,
    val hasChanges: Boolean = false,
    val shouldShowTutorial: Boolean = true
)

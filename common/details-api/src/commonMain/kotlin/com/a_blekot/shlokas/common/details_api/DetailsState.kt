package com.a_blekot.shlokas.common.details_api

import com.a_blekot.shlokas.common.data.ShlokaConfig

data class DetailsState(
    val config: ShlokaConfig = ShlokaConfig(),
    val hasChanges: Boolean = false
)

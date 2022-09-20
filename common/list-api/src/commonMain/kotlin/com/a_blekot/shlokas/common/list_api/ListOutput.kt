package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.PlayConfig
import com.a_blekot.shlokas.common.data.ShlokaConfig

sealed interface ListOutput {
    data class Play(val config: PlayConfig): ListOutput
    data class Details(val config: ShlokaConfig): ListOutput
    object Settings: ListOutput
    object Donations: ListOutput
    object ShareApp: ListOutput
    object InappReview: ListOutput
}
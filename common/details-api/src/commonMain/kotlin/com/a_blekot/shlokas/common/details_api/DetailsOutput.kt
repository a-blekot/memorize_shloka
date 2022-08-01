package com.a_blekot.shlokas.common.details_api

import com.a_blekot.shlokas.common.data.ShlokaConfig

sealed interface DetailsOutput {
    data class SaveConfig(val config: ShlokaConfig): DetailsOutput
}
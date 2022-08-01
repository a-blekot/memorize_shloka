package com.a_blekot.shlokas.common.details_impl

import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.utils.DispatcherProvider

data class DetailsDeps(
    val config: ShlokaConfig,
    val dispatchers: DispatcherProvider,
)

package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable

@Serializable
data class Shloka(
    val id: ShlokaId = ShlokaId.NONE,
    val title: String = "",
    val hasAudio: Boolean = true,
    val extra: Boolean = false,
)

package com.a_blekot.shlokas.common.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Shloka(
    val id: ShlokaId = ShlokaId.NONE,
    val title: String = "",
    val hasAudio: Boolean = true,
    val extra: Boolean = false,
): Parcelable

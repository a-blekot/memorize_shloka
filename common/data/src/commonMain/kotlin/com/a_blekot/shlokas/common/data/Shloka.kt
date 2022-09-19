package com.a_blekot.shlokas.common.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Shloka(
    val id: String = "",
    val title: String = "",
    val folder: String = "",
    val hasAudio: Boolean = true,
): Parcelable

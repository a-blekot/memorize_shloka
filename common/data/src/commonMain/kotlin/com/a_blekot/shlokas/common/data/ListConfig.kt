package com.a_blekot.shlokas.common.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

private const val EXT = ".json"

@Parcelize
@Serializable
data class ListConfig(
    val id: ListId,
    val title: String = "",
    val list: List<ShlokaConfig> = emptyList(),
): Parcelable {
    val fileName
        get() = id.id + EXT
}

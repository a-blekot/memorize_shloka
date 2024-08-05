package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable

private const val EXT = ".json"

@Serializable
data class ListConfig(
    val id: ListId,
    val title: String = "",
    val list: List<ShlokaConfig> = emptyList(),
) {
    val fileName
        get() = id.id + EXT
}

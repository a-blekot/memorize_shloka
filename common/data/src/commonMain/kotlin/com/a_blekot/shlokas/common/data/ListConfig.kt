package com.a_blekot.shlokas.common.data

import kotlin.random.Random
import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue

private const val EXT = ".json"
private const val VISIBLE_DIGITS = 3
private const val DEFAULT_TITLE = "Shlokas"
private const val DEFAULT_DESCRIPTION = "description is here"

@Serializable
data class ListConfig(
    val id: Long = Random.nextLong().absoluteValue,
    val title: String = DEFAULT_TITLE + " " + id.toString().take(VISIBLE_DIGITS),
    val description: String = DEFAULT_DESCRIPTION,
    val list: List<ShlokaConfig> = emptyList(),
) {
    val fileName
        get() = title + EXT
}

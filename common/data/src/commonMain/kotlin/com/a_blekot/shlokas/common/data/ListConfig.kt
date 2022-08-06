package com.a_blekot.shlokas.common.data

import kotlin.random.Random
import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue

private const val EXT = ".json"
private const val VISIBLE_DIGITS = 3
private const val DEFAULT_TITLE = "Shlokas"
private const val EMPTY_TEXT = ""

// /document/9016-4EF8:Music/Bhaktivaibhava/

@Serializable
data class ListConfig(
    val id: Long = Random.nextLong().absoluteValue,
    val title: String = EMPTY_TEXT,//DEFAULT_TITLE + " " + id.toString().take(VISIBLE_DIGITS),
    val description: String = EMPTY_TEXT,
    val folder: String = EMPTY_TEXT,
    val configName: String = EMPTY_TEXT,
    val list: List<ShlokaConfig> = emptyList(),
) {
    val fileName
        get() = configName + EXT
}

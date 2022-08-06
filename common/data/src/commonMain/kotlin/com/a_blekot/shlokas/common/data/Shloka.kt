package com.a_blekot.shlokas.common.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.benasher44.uuid.uuid4
import kotlinx.serialization.Serializable

private const val NO_FILE = ""
private const val VISIBLE_DIGITS = 3
private const val DEFAULT_TITLE = "Untitled"
private const val EMPTY_TEXT = ""

@Parcelize
@Serializable
data class Shloka(
    val id: Int = uuid4().hashCode(),
    val title: String = DEFAULT_TITLE + " " + id.toString().take(VISIBLE_DIGITS),
    val folder: String = NO_FILE,
    val fileName: String = NO_FILE,
    val sanskrit: String = EMPTY_TEXT,
    val words: String = EMPTY_TEXT,
    val translation: String = EMPTY_TEXT,
): Parcelable

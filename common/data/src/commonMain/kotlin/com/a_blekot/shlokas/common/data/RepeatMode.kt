package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable


@Serializable
enum class RepeatMode {
    ONE_LINE,
    TWO_LINES,
    FOUR_LINES,
    QUICK_LEARN;

    companion object {
        fun fromOrdinal(ordinal: Int, defaultValue: RepeatMode = ONE_LINE) =
            values().firstOrNull { it.ordinal == ordinal } ?: defaultValue
    }
}
package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable


@Serializable
enum class RepeatMode(val partsCount: Int) {
    ONE_LINE(partsCount = 4),
    TWO_LINES(partsCount = 2),
    FOUR_LINES(partsCount = 1);

    companion object {
        fun fromOrdinal(ordinal: Int, defaultValue: RepeatMode = ONE_LINE) =
            values().firstOrNull { it.ordinal == ordinal } ?: defaultValue
    }
}
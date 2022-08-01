package com.a_blekot.shlokas.common.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Chunk(
    val startMs: Long = 0L,
    val endMs: Long = 0L,
) : Parcelable {
    operator fun plus(next: Chunk): Chunk {
        return Chunk(startMs, next.endMs)
    }
}

val Chunk.durationMs
    get() = (endMs - startMs).coerceAtLeast(0L)

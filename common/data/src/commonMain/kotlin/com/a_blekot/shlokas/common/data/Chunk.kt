package com.a_blekot.shlokas.common.data

import kotlinx.serialization.Serializable

@Serializable
data class Chunk(
    val startMs: Long = 0L,
    val endMs: Long = 0L,
) {
    operator fun plus(next: Chunk): Chunk {
        return Chunk(startMs, next.endMs)
    }
}

val Chunk.durationMs
    get() = (endMs - startMs).coerceAtLeast(0L)

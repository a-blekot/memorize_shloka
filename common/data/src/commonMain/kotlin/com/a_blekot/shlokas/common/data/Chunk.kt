package com.a_blekot.shlokas.common.data

data class Chunk(
    val startMs: Long,
    val endMs: Long,
) {
    operator fun plus(next: Chunk): Chunk {
        return Chunk(startMs, next.endMs)
    }
}

val Chunk.durationMs
    get() = (endMs - startMs).coerceAtLeast(0L)

package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.Chunk

data class PlayTask(
    val startMs: Long,
    val endMs: Long,
    val currentRepeat: Int,
    var absoluteStartMs: Long,
) : Task {
    constructor(
        chunk: Chunk,
        currentRepeat: Int,
        absoluteStartMs: Long,
    ) : this(
        startMs = chunk.startMs,
        endMs = chunk.endMs,
        currentRepeat = currentRepeat,
        absoluteStartMs = absoluteStartMs,
    )

    override val duration: Long
        get() = (endMs - startMs).coerceAtLeast(0L)
}

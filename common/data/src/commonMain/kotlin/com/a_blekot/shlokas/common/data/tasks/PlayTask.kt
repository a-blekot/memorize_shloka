package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.Chunk

data class PlayTask(
    val startMs: Long,
    val endMs: Long,
    val currentRepeat: Int,
) : Task {
    constructor(
        chunk: Chunk,
        currentRepeat: Int,
    ) : this(
        startMs = chunk.startMs,
        endMs = chunk.endMs,
        currentRepeat = currentRepeat,
    )

    override val duration: Long
        get() = (endMs - startMs).coerceAtLeast(0L)
}

package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.Chunk

data class PlayTask(
    val startMs: Long,
    val endMs: Long,
    var absoluteStartMs: Long,
) : Task {
    constructor(
        chunk: Chunk,
        absoluteStartMs: Long,
    ) : this(
        startMs = chunk.startMs,
        endMs = chunk.endMs,
        absoluteStartMs = absoluteStartMs,
    )

    override val duration: Long
        get() = (endMs - startMs).coerceAtLeast(0L)
}

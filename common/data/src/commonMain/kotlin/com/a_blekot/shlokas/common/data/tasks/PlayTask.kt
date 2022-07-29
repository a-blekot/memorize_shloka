package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.Chunk
import com.a_blekot.shlokas.common.data.Shloka

data class PlayTask(
    val shlokaId: Long,
    val title: String,
    val filePath: String,
    val description: String,
    val startMs: Long,
    val endMs: Long,
    val pauseAfter: Long,
) : Task {
    constructor(
        shloka: Shloka,
        chunk: Chunk,
        pauseAfter: Long,
    ) : this(
        shlokaId = shloka.id,
        title = shloka.title,
        filePath = shloka.filePath,
        description = shloka.description,
        startMs = chunk.startMs,
        endMs = chunk.endMs,
        pauseAfter = pauseAfter,
    )

    override val duration: Long
        get() = (endMs - startMs).coerceAtLeast(0L) + pauseAfter
}

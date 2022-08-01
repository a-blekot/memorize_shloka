package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.Shloka

data class SetTrackTask(
    val title: String,
    val filePath: String,
    val description: String,
    override val duration: Long = 0L,
) : Task {
    constructor(
        shloka: Shloka,
    ) : this(
        title = shloka.title,
        filePath = shloka.filePath,
        description = shloka.description,
    )
}

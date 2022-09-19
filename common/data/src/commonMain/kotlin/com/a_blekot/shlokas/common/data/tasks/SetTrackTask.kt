package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.Shloka

data class SetTrackTask(
    val index: Int,// 1 based index
    val id: String,
    val title: String,
    val folder: String,
    val hasAudio: Boolean,
    val description: String = "",
    override val duration: Long = 0L,
) : Task {
    constructor(
        index: Int,
        shloka: Shloka,
    ) : this(
        index = index,
        id = shloka.id,
        title = shloka.title,
        folder = shloka.folder,
        hasAudio = shloka.hasAudio
    )
}

package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.Shloka

data class SetTrackTask(
    val index: Int,// 1 based index
    val title: String,
    val folder: String,
    val fileName: String,
    val sanskrit: String,
    val words: String,
    val translation: String,
    override val duration: Long = 0L,
) : Task {
    constructor(
        index: Int,
        shloka: Shloka,
    ) : this(
        index = index,
        title = shloka.title,
        folder = shloka.folder,
        fileName = shloka.fileName,
        sanskrit = shloka.sanskrit,
        words = shloka.words,
        translation = shloka.translation,
    )
}

package com.a_blekot.shlokas.common.data.tasks

import com.a_blekot.shlokas.common.data.ShlokaId
import com.a_blekot.shlokas.common.data.speed

const val SPEECH_RATE = 0.8f

data class PlayTranslationTask(
    val id: ShlokaId,
    val currentRepeat: Int,
    val text: String = "",
) : Task {
    override val duration
        get() = ((text.length + 15) * 65L).speed(SPEECH_RATE)
}
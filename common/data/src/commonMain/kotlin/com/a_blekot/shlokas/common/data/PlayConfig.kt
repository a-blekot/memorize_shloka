package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.PauseTask
import com.a_blekot.shlokas.common.data.tasks.StopTask
import com.a_blekot.shlokas.common.data.tasks.Task
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize


@Parcelize
data class PlayConfig(
    val repeatMode: RepeatMode,
    val shlokas: List<ShlokaConfig>,
    val startShloka: ShlokaConfig? = null,
    val repeats: Int,
    val withSanskrit: Boolean,
    val withTranslation: Boolean,
    val pauseAfterEach: Long
) : Parcelable

fun PlayConfig.createTasks(): List<Task> {
    val tasks = mutableListOf<Task>()

    shlokas
        .filter { it.isSelected }
        .forEachIndexed { i, shlokaConfig ->
            val list = shlokaConfig.createTasks(
                index = i + 1,
                repeatMode = repeatMode,
                repeats = repeats,
                pauseMs = pauseAfterEach,
                withSanskrit = withSanskrit,
            )
            tasks.addAll(list)

            if (withTranslation) {
                val translationsList = shlokaConfig.createTranslationTasks(repeats, pauseAfterEach)
                tasks.addAll(translationsList)
            }

            tasks.add(PauseTask(pauseAfterEach))
        }

    tasks.add(StopTask)

    return tasks
}
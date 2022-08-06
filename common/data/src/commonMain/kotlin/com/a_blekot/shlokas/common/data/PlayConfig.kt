package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.PauseTask
import com.a_blekot.shlokas.common.data.tasks.StopTask
import com.a_blekot.shlokas.common.data.tasks.StopTask.duration
import com.a_blekot.shlokas.common.data.tasks.Task
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize


@Parcelize
data class PlayConfig(
    val week: Week,
    val shlokas: List<ShlokaConfig>,
    val repeats: Int,
    val pauseAfterEach: Long
) : Parcelable

val PlayConfig.durationMs
    get() = (shlokas.sumOf { it.durationMs + pauseAfterEach }) * repeats

fun PlayConfig.createTasks(): List<Task> {
    val tasks = mutableListOf<Task>()

    var absoluteStartMs = 0L
    shlokas
        .filter { it.isSelected }
        .forEachIndexed { i, shlokaConfig ->
            val list = shlokaConfig.createTasks(i + 1, week, repeats, absoluteStartMs)
            tasks.addAll(list)
            tasks.add(PauseTask(pauseAfterEach))

            absoluteStartMs += list.sumOf { it.duration } + pauseAfterEach
        }

    tasks.add(StopTask)

    return tasks
}
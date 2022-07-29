package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.PauseTask
import com.a_blekot.shlokas.common.data.tasks.Task

private const val DEFAULT_REPEATS = 10
private const val DEFAULT_PAUSE = 500L

data class PlayConfig(
    val week: Week,
    val shlokas: List<ShlokaConfig>,
    val repeats: Int = DEFAULT_REPEATS,
    val pauseAfterEach: Long = DEFAULT_PAUSE
)

val PlayConfig.durationMs
    get() = (shlokas.sumOf { it.durationMs + pauseAfterEach } ) * repeats

fun PlayConfig.createTasks(): List<Task> {
    val tasks = mutableListOf<Task>()

    shlokas.forEach { shlokaConfig ->
        tasks.addAll(shlokaConfig.createTasks(week, repeats))
        tasks.add(PauseTask(pauseAfterEach))
    }

    return tasks
}
package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.PlayTask

private const val CHUNKS_SIZE = 4
private const val DEFAULT_PAUSE = 150L

data class ShlokaConfig(
    val shloka: Shloka,
    val chunks: List<Chunk>,
    val pauseAfterEach: Long = DEFAULT_PAUSE,
)

val ShlokaConfig.durationMs
    get() = chunks.sumOf { it.durationMs + pauseAfterEach }

fun ShlokaConfig.createTasks(week: Week, repeats: Int): List<PlayTask> {
    check(chunks.size == CHUNKS_SIZE) {
        "Shloka should consist of four padas!"
    }

    val tasks = mutableListOf<PlayTask>()

    when (week) {
        Week.FIRST -> {
            chunks.forEach { chunk ->
                repeat(repeats) {
                    tasks.add(PlayTask(shloka, chunk, pauseAfterEach))
                }
            }
        }

        Week.SECOND -> {
            repeat(repeats) {
                tasks.add(PlayTask(shloka, chunks[0] + chunks[1], pauseAfterEach))
            }
            repeat(repeats) {
                tasks.add(PlayTask(shloka, chunks[2] + chunks[3], pauseAfterEach))
            }
        }

        Week.THIRD -> {
            repeat(repeats) {
                tasks.add(PlayTask(shloka, chunks[0] + chunks[3], pauseAfterEach))
            }
        }
    }

    return tasks
}
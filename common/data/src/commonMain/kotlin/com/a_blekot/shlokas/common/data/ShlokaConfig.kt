package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.*
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

private const val CHUNKS_SIZE = 4

@Parcelize
@Serializable
data class ShlokaConfig(
    val shloka: Shloka = Shloka(),
    val chunks: List<Chunk> = defaultChunks,
    val isSelected: Boolean = true
): Parcelable

val defaultChunks
    get() = MutableList(CHUNKS_SIZE) { Chunk() }

val ShlokaConfig.durationMs
    get() = chunks.sumOf { it.durationMs }

fun ShlokaConfig.createTasks(index: Int, week: Week, repeats: Int, pauseMs: Long): List<Task> {
    check(chunks.size == CHUNKS_SIZE) {
        "Shloka should consist of four padas!"
    }

    val tasks = mutableListOf<Task>()

    tasks.add(SetTrackTask(index, shloka))
    tasks.add(IdleTask)

    when (week) {
        Week.FIRST -> {
            chunks.forEach { chunk ->
                repeat(repeats) {
                    tasks.add(PlayTask(chunk, it + 1))
                    if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
                    tasks.add(PauseTask(pauseMs))
                }
            }
        }

        Week.SECOND -> {
            repeat(repeats) {
                val chunk = chunks[0] + chunks[1]
                tasks.add(PlayTask(chunk, it + 1))
                if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
                tasks.add(PauseTask(pauseMs))
            }
            repeat(repeats) {
                val chunk = chunks[2] + chunks[3]
                tasks.add(PlayTask(chunk, it + 1))
                if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
                tasks.add(PauseTask(pauseMs))
            }
        }

        Week.THIRD -> {
            repeat(repeats) {
                val chunk = chunks[0] + chunks[3]
                tasks.add(PlayTask(chunk, it + 1))
                if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
                tasks.add(PauseTask(pauseMs))
            }
        }
    }

    return tasks
}
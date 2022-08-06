package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.PauseTask
import com.a_blekot.shlokas.common.data.tasks.PlayTask
import com.a_blekot.shlokas.common.data.tasks.SetTrackTask
import com.a_blekot.shlokas.common.data.tasks.Task
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

private const val CHUNKS_SIZE = 4
private const val DEFAULT_PAUSE = 150L

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
    get() = chunks.sumOf { it.durationMs + DEFAULT_PAUSE }

fun ShlokaConfig.createTasks(index: Int, week: Week, repeats: Int, startMs: Long): List<Task> {
    check(chunks.size == CHUNKS_SIZE) {
        "Shloka should consist of four padas!"
    }

    val tasks = mutableListOf<Task>()

    var absoluteStartMs = startMs

    tasks.add(SetTrackTask(index, shloka))

    when (week) {
        Week.FIRST -> {
            chunks.forEach { chunk ->
                repeat(repeats) {
                    tasks.add(PlayTask(chunk, it + 1, absoluteStartMs))
                    tasks.add(PauseTask(DEFAULT_PAUSE))
                    absoluteStartMs += (chunk.durationMs + DEFAULT_PAUSE)
                }
            }
        }

        Week.SECOND -> {
            repeat(repeats) {
                val chunk = chunks[0] + chunks[1]
                tasks.add(PlayTask(chunk, it + 1, absoluteStartMs))
                tasks.add(PauseTask(DEFAULT_PAUSE))
                absoluteStartMs += (chunk.durationMs + DEFAULT_PAUSE)
            }
            repeat(repeats) {
                val chunk = chunks[2] + chunks[3]
                tasks.add(PlayTask(chunk, it + 1, absoluteStartMs))
                tasks.add(PauseTask(DEFAULT_PAUSE))
                absoluteStartMs += (chunk.durationMs + DEFAULT_PAUSE)
            }
        }

        Week.THIRD -> {
            repeat(repeats) {
                val chunk = chunks[0] + chunks[3]
                tasks.add(PlayTask(chunk, it + 1, absoluteStartMs))
                tasks.add(PauseTask(DEFAULT_PAUSE))
                absoluteStartMs += (chunk.durationMs + DEFAULT_PAUSE)
            }
        }
    }

    return tasks
}
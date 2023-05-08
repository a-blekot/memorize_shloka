package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.*
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

private const val CHUNKS_SIZE = 4 // Could be 8 or even 5

@Parcelize
@Serializable
data class ShlokaConfig(
    val shloka: Shloka = Shloka(),
    val chunks: List<Chunk> = defaultChunks,
    val isSelected: Boolean = true
) : Parcelable

val defaultChunks
    get() = MutableList(CHUNKS_SIZE) { Chunk() }

val ShlokaConfig.durationMs
    get() = chunks.sumOf { it.durationMs }

fun ShlokaConfig.createTasks(
    index: Int,
    repeatMode: RepeatMode,
    repeats: Int,
    pauseMs: Long,
    withSanskrit: Boolean,
): List<Task> {
    val tasks = mutableListOf<Task>()
    tasks.add(SetTrackTask(index, shloka))

    when {
        !withSanskrit -> return tasks.apply { add(IdleTask) }
        !shloka.hasAudio -> return tasks.apply { add(NoAudioTask) }
        else -> tasks.add(IdleTask)
    }

    when (repeatMode) {
        RepeatMode.ONE_LINE -> {
            chunks.forEach { chunk ->
                repeat(repeats) {
                    tasks.add(PlayTask(chunk, it + 1))
                    if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
                    tasks.add(PauseTask(pauseMs))
                }
            }
        }

        RepeatMode.TWO_LINES -> {
            chunks
                .windowed(2, 2, partialWindows = true)
                .forEach { window ->
                    repeat(repeats) {
                        val chunk = window.first() + window.last()
                        tasks.add(PlayTask(chunk, it + 1))
                        if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
                        tasks.add(PauseTask(pauseMs))
                    }
                }
        }

        RepeatMode.FOUR_LINES -> {
            chunks
                .windowed(4, 4, partialWindows = true)
                .forEach { window ->
                    repeat(repeats) {
                        val chunk = window.first() + window.last()
                        tasks.add(PlayTask(chunk, it + 1))
                        if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
                        tasks.add(PauseTask(pauseMs))
                    }
                }
        }
    }

    return tasks
}

fun ShlokaConfig.createTranslationTasks(repeats: Int, pauseMs: Long): List<Task> {
    val tasks = mutableListOf<Task>()

    repeat(repeats) {
        tasks.add(PlayTranslationTask(shloka.id, currentRepeat = it + 1))
        if (it == repeats - 1) tasks.add(ResetCounterTask(pauseMs))
        tasks.add(PauseTask(pauseMs))
    }

    return tasks
}
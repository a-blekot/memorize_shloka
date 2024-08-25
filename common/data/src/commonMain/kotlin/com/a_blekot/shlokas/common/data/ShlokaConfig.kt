package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.tasks.*
import kotlinx.serialization.Serializable

private const val CHUNKS_SIZE = 4 // Could be 8 or even 5

@Serializable
data class ShlokaConfig(
    val shloka: Shloka = Shloka(),
    val chunks: List<Chunk> = defaultChunks,
    val isSelected: Boolean = true
)

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
    tasks.add(ResetCounterTask(pauseMs))

    when {
        !withSanskrit -> return tasks.apply { add(IdleTask) }
        !shloka.hasAudio -> return tasks.apply { add(NoAudioTask) }
        else -> tasks.add(IdleTask)
    }

    when (repeatMode) {
        RepeatMode.ONE_LINE -> tasks.addOneLines(chunks, repeats, pauseMs)
        RepeatMode.TWO_LINES -> tasks.addTwoLines(chunks, repeats, pauseMs)
        RepeatMode.FOUR_LINES -> tasks.addFourLines(chunks, repeats, pauseMs)

        RepeatMode.QUICK_LEARN -> {
            tasks.addOneLines(chunks, repeats, pauseMs)
            tasks.addTwoLines(chunks, repeats, pauseMs)
            tasks.addFourLines(chunks, repeats, pauseMs)
        }
    }

    return tasks
}

private fun MutableList<Task>.addOneLines(chunks: List<Chunk>, repeats: Int, pauseMs: Long) =
    chunks.forEach { chunk ->
        repeat(repeats) {
            add(PlayTask(chunk, it + 1))
            if (it == repeats - 1) add(ResetCounterTask(pauseMs))
            add(PauseTask(pauseMs))
        }
    }

private fun MutableList<Task>.addTwoLines(chunks: List<Chunk>, repeats: Int, pauseMs: Long) =
    chunks
        .windowed(2, 2, partialWindows = true)
        .forEach { window ->
            repeat(repeats) {
                val chunk = window.first() + window.last()
                add(PlayTask(chunk, it + 1))
                if (it == repeats - 1) add(ResetCounterTask(pauseMs))
                add(PauseTask(pauseMs))
            }
        }

private fun MutableList<Task>.addFourLines(chunks: List<Chunk>, repeats: Int, pauseMs: Long) =
    chunks
        .windowed(4, 4, partialWindows = true)
        .forEach { window ->
            repeat(repeats) {
                val chunk = window.first() + window.last()
                add(PlayTask(chunk, it + 1))
                if (it == repeats - 1) add(ResetCounterTask(pauseMs))
                add(PauseTask(pauseMs))
            }
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
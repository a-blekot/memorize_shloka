package com.a_blekot.shlokas.common.details_impl.store

sealed interface DetailsIntent {
    data class Title(val value: String) : DetailsIntent
    data class FilePath(val value: String) : DetailsIntent
    data class Sanskrit(val value: String) : DetailsIntent
    data class WordsTranslation(val value: String) : DetailsIntent
    data class Translation(val value: String) : DetailsIntent
    data class ChunkStart(val index: Int, val value: Long) : DetailsIntent
    data class ChunkEnd(val index: Int, val value: Long) : DetailsIntent
    data class IsSelected(val isSelected: Boolean) : DetailsIntent
    data class Pause(val value: Long) : DetailsIntent
}

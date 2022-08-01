package com.a_blekot.shlokas.common.details_impl.store

sealed interface DetailsIntent {
    data class Title(val value: String) : DetailsIntent
    data class FilePath(val value: String) : DetailsIntent
    data class Description(val value: String) : DetailsIntent
    data class ChunkStart(val index: Int, val value: Long) : DetailsIntent
    data class ChunkEnd(val index: Int, val value: Long) : DetailsIntent
    data class Pause(val value: Long) : DetailsIntent
}

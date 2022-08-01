package com.a_blekot.shlokas.common.details_api

import com.arkivanov.decompose.value.Value

interface DetailsComponent {
    val flow: Value<DetailsState>

    fun saveChanges() {}
    fun setTitle(title: String) {}
    fun setFilePath(filePath: String) {}
    fun setDescription(description: String) {}
    fun setChunkStart(index: Int, startMs: Long) {}
    fun setChunkEnd(index: Int, endMs: Long) {}
    fun setPause(pause: Long) {}
}
package com.a_blekot.shlokas.common.details_api

import com.arkivanov.decompose.value.Value

interface DetailsComponent {
    val flow: Value<DetailsState>

    fun saveChanges() {}
    fun play() {}
    fun setTitle(title: String) {}
    fun setFilePath(filePath: String) {}
    fun setSanskrit(value: String) {}
    fun setWords(value: String) {}
    fun setTranslation(value: String) {}
    fun setChunkStart(index: Int, startMs: Long) {}
    fun setChunkEnd(index: Int, endMs: Long) {}
    fun setSelected(isSelected: Boolean) {}
    fun setPause(pause: Long) {}
}
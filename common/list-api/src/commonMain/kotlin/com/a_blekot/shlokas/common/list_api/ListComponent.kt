package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.arkivanov.decompose.value.Value

interface ListComponent {
    val flow: Value<ListState>

    fun add() {}
    fun save() {}
    fun remove(id: String) {}
    fun moveUp(id: String) {}
    fun moveDown(id: String) {}
    fun select(id: String, isSelected: Boolean) {}
    fun details(config: ShlokaConfig) {}
    fun play() {}
    fun play(config: ShlokaConfig) {}
    fun settings() {}
    fun donations() {}
    fun shareApp() {}
    fun saveShloka(config: ShlokaConfig) {}
    fun onTutorialCompleted() {}
    fun onTutorialSkipped() {}
    fun setList(id: String) {}

    fun onPreRatingAccepted() {}
    fun onPreRatingClosed() {}
}
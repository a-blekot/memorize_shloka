package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.data.ShlokaId
import com.arkivanov.decompose.value.Value

interface ListComponent {
    val flow: Value<ListState>

    fun resolveDescription(id: ShlokaId): String = ""
    fun add() {}
    fun save() {}
    fun remove(id: ShlokaId) {}
    fun moveUp(id: ShlokaId) {}
    fun moveDown(id: ShlokaId) {}
    fun select(id: ShlokaId, isSelected: Boolean) {}
    fun details(config: ShlokaConfig) {}
    fun play() {}
    fun play(config: ShlokaConfig) {}
    fun youtube() {}
    fun settings() {}
    fun donations() {}
    fun shareApp() {}
    fun saveShloka(config: ShlokaConfig) {}
    fun onTutorialCompleted() {}
    fun onTutorialSkipped() {}
    fun setList(type: ListId) {}

    fun onPreRatingAccepted() {}
    fun onPreRatingClosed() {}
}
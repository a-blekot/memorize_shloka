package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.arkivanov.decompose.value.Value

interface ListComponent {
    val flow: Value<ListState>

    fun add() {}
    fun save() {}
    fun remove(id: Int) {}
    fun moveUp(id: Int) {}
    fun moveDown(id: Int) {}
    fun select(id: Int, isSelected: Boolean) {}
    fun details(config: ShlokaConfig) {}
    fun play() {}
    fun play(config: ShlokaConfig) {}
    fun settings() {}
    fun saveShloka(config: ShlokaConfig) {}
}
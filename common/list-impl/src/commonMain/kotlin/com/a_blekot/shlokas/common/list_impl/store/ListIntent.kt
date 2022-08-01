package com.a_blekot.shlokas.common.list_impl.store

import com.a_blekot.shlokas.common.data.ShlokaConfig

sealed interface ListIntent {
    object Add : ListIntent
    object Save : ListIntent
    data class Title(val title: String) : ListIntent
    data class Remove(val id: Int) : ListIntent
    data class MoveUp(val id: Int) : ListIntent
    data class MoveDown(val id: Int) : ListIntent
    data class SaveShloka(val config: ShlokaConfig) : ListIntent
}

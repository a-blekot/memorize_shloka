package com.a_blekot.shlokas.common.list_impl.store

import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaConfig
import com.a_blekot.shlokas.common.data.ShlokaId

sealed interface ListIntent {
    object Add : ListIntent
    object Save : ListIntent
    object CheckLocale : ListIntent
    object CheckPreRating : ListIntent
    object PreRatingAccepted : ListIntent
    object PreRatingClosed : ListIntent
    object TutorialCompleted : ListIntent
    object TutorialSkipped : ListIntent
    data class SetList(val type: ListId) : ListIntent
    data class Title(val title: String) : ListIntent
    data class Remove(val id: ShlokaId) : ListIntent
    data class MoveUp(val id: ShlokaId) : ListIntent
    data class MoveDown(val id: ShlokaId) : ListIntent
    data class Select(val id: ShlokaId, val isSelected: Boolean) : ListIntent
    data class SaveShloka(val config: ShlokaConfig) : ListIntent
}

package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.ListId
import kotlinx.serialization.Serializable

@Serializable
data class ListPresentation(
    val type: ListId,
    val title: String,
    val isSelected: Boolean
)
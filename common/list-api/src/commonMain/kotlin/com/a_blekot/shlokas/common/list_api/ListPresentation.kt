package com.a_blekot.shlokas.common.list_api

import com.a_blekot.shlokas.common.data.ListId
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class ListPresentation(
    val type: ListId,
    val title: String,
    val isSelected: Boolean
): Parcelable
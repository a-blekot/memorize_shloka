package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.ListId.SB_1
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
enum class ListId(val id: String): Parcelable {
    BG("BG"),
    NI("NI"),
    SB_1("SB_1"),
    SB_2("SB_2"),
    SB_3("SB_3"),
    SB_4("SB_4"),
    SB_5("SB_5"),
    SB_6("SB_6"),
}

fun String.toListType() =
    ListId.values().firstOrNull { it.id == this } ?: SB_1
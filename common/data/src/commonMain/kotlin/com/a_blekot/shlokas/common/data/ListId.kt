package com.a_blekot.shlokas.common.data

import com.a_blekot.shlokas.common.data.ListId.SB_1
import kotlinx.serialization.Serializable

@Serializable
enum class ListId(val id: String) {
    BG("BG"),
    NI("NI"),
    NK("NK"),
    ISO("ISO"),
    NOD("NOD"),
    SB_1("SB_1"),
    SB_2("SB_2"),
    SB_3("SB_3"),
    SB_4("SB_4"),
    SB_5("SB_5"),
    SB_6("SB_6"),
}

fun String.toListType() =
    ListId.entries.firstOrNull { it.id == this } ?: SB_1
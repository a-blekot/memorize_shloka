package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.resources.MR

private const val SB = "SB"
private const val NI = "NI"

fun getTitle(id: String) =
    when {
        id.startsWith(SB) -> StringsSB.getTitle(id)
        id.startsWith(NI) -> StringsNI.getTitle(id)
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getSanskrit(id: String) =
    when {
        id.startsWith(SB) -> StringsSB.getSanskrit(id)
        id.startsWith(NI) -> StringsNI.getSanskrit(id)
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getWords(id: String) =
    when {
        id.startsWith(SB) -> StringsSB.getWords(id)
        id.startsWith(NI) -> StringsNI.getWords(id)
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getTranslation(id: String) =
    when {
        id.startsWith(SB) -> StringsSB.getTranslation(id)
        id.startsWith(NI) -> StringsNI.getTranslation(id)
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getDescription(id: String) =
    when {
        id.startsWith(SB) -> StringsSB.getDescription(id)
        id.startsWith(NI) -> StringsNI.getDescription(id)
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getListTitle(id: String) =
    when (id) {
        "sb_1_canto_config" -> MR.strings.title_SB_1_canto
        "ni_config" -> MR.strings.title_NI
        else -> throw IllegalArgumentException("unknown list config id: $id")
    }

private fun String.takeOrNull(n: Int) = if (length < n) null else take(n)
package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.Locales
import com.a_blekot.shlokas.common.data.ShlokaId

enum class Shastra(
    val id: String,
    val ttsEn: String,
    val ttsRu: String,
    val ttsUk: String,
) {
    SB(
        id = "SB",
        ttsEn = "Shrimad Bhagavatam",
        ttsRu = "Шримад Бхагаватам",
        ttsUk = "Шрімад Бхаґаватам",
    ),
    NI(
        id = "NI",
        ttsEn = "Nectar of Instructions",
        ttsRu = "Нектар наставлений",
        ttsUk = "Нектар настанов",
    ),
    NK(
        id = "NK",
        ttsEn = "Nrisimha kavacha",
        ttsRu = "Нрисимха кавача",
        ttsUk = "Нрісімха кавача",
    ),
    ISO(
        id = "ISO",
        ttsEn = "Shri Ishopanishad",
        ttsRu = "Шри Ишопанишад",
        ttsUk = "Шрі Ішопанішад",
    ),
    NOD(
        id = "NOD",
        ttsEn = "Nectar of devotion",
        ttsRu = "Нектар преданности",
        ttsUk = "Нектар відданості",
    ),
    BG(
        id = "BG",
        ttsEn = "Bhagavad-gita",
        ttsRu = "Бхагавад-гита",
        ttsUk = "Бхаґавад-ґіта",
    );

    fun localizedTts(locale: String) =
        when (locale) {
            Locales.ru -> ttsRu
            Locales.uk -> ttsUk
            else -> ttsEn
        }

    companion object {
        fun byId(id: String) =
            values().firstOrNull { it.id == id }
    }
}

fun ShlokaId.toVerseName(locale: String) =
    id.replace("_0", " ")
        .replace("_", " ")
        .replaceShastraId(locale)

private fun String.replaceShastraId(locale: String): String {
    var result = this

    Shastra.entries.forEach { shastra ->
        val title = Shastra.byId(shastra.id)?.localizedTts(locale).orEmpty()
        result = result.replace(shastra.id, title)
    }

    return result
}


fun String.removeDiacritics() =
    replace("̄а̄", "a")
        .replace("̄ӣ", "и")
        .replace("̄ı̄", "і")
        .replace("̄ӯ", "у")
        .replace("̄р̣", "ри")
        .replace("̄р̣̄", "ри")
        .replace("̄л̣", "ли")
        .replace("̄ш́", "ш")
        .replace("̄м̇", "м")
        .replace("̄х̣", "х")
        .replace("̄н̇", "н")
        .replace("̄н̃", "н")
        .replace("̄н̣", "н")
        .replace("̄т̣", "т")
        .replace("̄д̣", "д")
        .replace("̄т̣", "т")
        .replace("̄д̣", "д")
        .replace("̄ā", "a")
        .replace("̄ī", "i")
        .replace("̄ū", "u")
        .replace("̄ṛ", "ree")
        .replace("̄ṝ", "ree")
        .replace("̄ḷ", "lee")
        .replace("̄ḹ", "lee")
        .replace("̄ś", "sh")
        .replace("̄ṣ", "sh")
        .replace("̄ṁ", "m")
        .replace("̄ḥ", "h")
        .replace("̄ṅ", "n")
        .replace("̄ñ", "n")
        .replace("̄ṇ", "n")
        .replace("̄ṭ", "t")
        .replace("̄ḍ", "d")

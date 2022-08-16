package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.resources.MR


object StringsNI {

    fun getTitle(id: String) =
        when (id) {
            "NI_01" -> MR.strings.title_NI_01
            "NI_02" -> MR.strings.title_NI_02
            "NI_03" -> MR.strings.title_NI_03
            "NI_04" -> MR.strings.title_NI_04
            "NI_05" -> MR.strings.title_NI_05
            "NI_06" -> MR.strings.title_NI_06
            "NI_07" -> MR.strings.title_NI_07
            "NI_08" -> MR.strings.title_NI_08
            "NI_09" -> MR.strings.title_NI_09
            "NI_10" -> MR.strings.title_NI_10
            "NI_11" -> MR.strings.title_NI_11
            else -> throw IllegalArgumentException("unknown shloka id: $id")
        }

    fun getSanskrit(id: String) =
        when (id) {
            "NI_01" -> MR.strings.sansk_NI_01
            "NI_02" -> MR.strings.sansk_NI_02
            "NI_03" -> MR.strings.sansk_NI_03
            "NI_04" -> MR.strings.sansk_NI_04
            "NI_05" -> MR.strings.sansk_NI_05
            "NI_06" -> MR.strings.sansk_NI_06
            "NI_07" -> MR.strings.sansk_NI_07
            "NI_08" -> MR.strings.sansk_NI_08
            "NI_09" -> MR.strings.sansk_NI_09
            "NI_10" -> MR.strings.sansk_NI_10
            "NI_11" -> MR.strings.sansk_NI_11
            else -> throw IllegalArgumentException("unknown shloka id: $id")
        }

    fun getWords(id: String) =
        when (id) {
            "NI_01" -> MR.strings.words_NI_01
            "NI_02" -> MR.strings.words_NI_02
            "NI_03" -> MR.strings.words_NI_03
            "NI_04" -> MR.strings.words_NI_04
            "NI_05" -> MR.strings.words_NI_05
            "NI_06" -> MR.strings.words_NI_06
            "NI_07" -> MR.strings.words_NI_07
            "NI_08" -> MR.strings.words_NI_08
            "NI_09" -> MR.strings.words_NI_09
            "NI_10" -> MR.strings.words_NI_10
            "NI_11" -> MR.strings.words_NI_11
            else -> throw IllegalArgumentException("unknown shloka id: $id")
        }

    fun getTranslation(id: String) =
        when (id) {
            "NI_01" -> MR.strings.trans_NI_01
            "NI_02" -> MR.strings.trans_NI_02
            "NI_03" -> MR.strings.trans_NI_03
            "NI_04" -> MR.strings.trans_NI_04
            "NI_05" -> MR.strings.trans_NI_05
            "NI_06" -> MR.strings.trans_NI_06
            "NI_07" -> MR.strings.trans_NI_07
            "NI_08" -> MR.strings.trans_NI_08
            "NI_09" -> MR.strings.trans_NI_09
            "NI_10" -> MR.strings.trans_NI_10
            "NI_11" -> MR.strings.trans_NI_11
            else -> throw IllegalArgumentException("unknown shloka id: $id")
        }

    fun getDescription(id: String) =
        when (id) {
            "NI_01" -> MR.strings.descr_NI_01
            "NI_02" -> MR.strings.descr_NI_02
            "NI_03" -> MR.strings.descr_NI_03
            "NI_04" -> MR.strings.descr_NI_04
            "NI_05" -> MR.strings.descr_NI_05
            "NI_06" -> MR.strings.descr_NI_06
            "NI_07" -> MR.strings.descr_NI_07
            "NI_08" -> MR.strings.descr_NI_08
            "NI_09" -> MR.strings.descr_NI_09
            "NI_10" -> MR.strings.descr_NI_10
            "NI_11" -> MR.strings.descr_NI_11
            else -> throw IllegalArgumentException("unknown shloka id: $id")
        }
}
package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.resources.MR

fun getSanskrit(id: String) =
    when (id) {
        "SB_1_1_1" -> MR.strings.sansk_SB_1_1_1
        "SB_1_1_10" -> MR.strings.sansk_SB_1_1_10
        "SB_1_2_6" -> MR.strings.sansk_SB_1_2_6
        "SB_1_2_8" -> MR.strings.sansk_SB_1_2_8
        "SB_1_2_11" -> MR.strings.sansk_SB_1_2_11
        "SB_1_2_16" -> MR.strings.sansk_SB_1_2_16
        "SB_1_2_17" -> MR.strings.sansk_SB_1_2_17
        "SB_1_2_18" -> MR.strings.sansk_SB_1_2_18
        "SB_1_3_28" -> MR.strings.sansk_SB_1_3_28
        "SB_1_3_43" -> MR.strings.sansk_SB_1_3_43
        "SB_1_5_11" -> MR.strings.sansk_SB_1_5_11
        "SB_1_5_18" -> MR.strings.sansk_SB_1_5_18
        "SB_1_7_6" -> MR.strings.sansk_SB_1_7_6
        "SB_1_7_10" -> MR.strings.sansk_SB_1_7_10
        "SB_1_8_26" -> MR.strings.sansk_SB_1_8_26
        "SB_1_8_42" -> MR.strings.sansk_SB_1_8_42
        "SB_1_13_10" -> MR.strings.sansk_SB_1_13_10
        "SB_1_18_13" -> MR.strings.sansk_SB_1_18_13
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getWords(id: String) =
    when (id) {
        "SB_1_1_1" -> MR.strings.words_SB_1_1_1
        "SB_1_1_10" -> MR.strings.words_SB_1_1_10
        "SB_1_2_6" -> MR.strings.words_SB_1_2_6
        "SB_1_2_8" -> MR.strings.words_SB_1_2_8
        "SB_1_2_11" -> MR.strings.words_SB_1_2_11
        "SB_1_2_16" -> MR.strings.words_SB_1_2_16
        "SB_1_2_17" -> MR.strings.words_SB_1_2_17
        "SB_1_2_18" -> MR.strings.words_SB_1_2_18
        "SB_1_3_28" -> MR.strings.words_SB_1_3_28
        "SB_1_3_43" -> MR.strings.words_SB_1_3_43
        "SB_1_5_11" -> MR.strings.words_SB_1_5_11
        "SB_1_5_18" -> MR.strings.words_SB_1_5_18
        "SB_1_7_6" -> MR.strings.words_SB_1_7_6
        "SB_1_7_10" -> MR.strings.words_SB_1_7_10
        "SB_1_8_26" -> MR.strings.words_SB_1_8_26
        "SB_1_8_42" -> MR.strings.words_SB_1_8_42
        "SB_1_13_10" -> MR.strings.words_SB_1_13_10
        "SB_1_18_13" -> MR.strings.words_SB_1_18_13
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getTranslation(id: String) =
    when (id) {
        "SB_1_1_1" -> MR.strings.trans_SB_1_1_1
        "SB_1_1_10" -> MR.strings.trans_SB_1_1_10
        "SB_1_2_6" -> MR.strings.trans_SB_1_2_6
        "SB_1_2_8" -> MR.strings.trans_SB_1_2_8
        "SB_1_2_11" -> MR.strings.trans_SB_1_2_11
        "SB_1_2_16" -> MR.strings.trans_SB_1_2_16
        "SB_1_2_17" -> MR.strings.trans_SB_1_2_17
        "SB_1_2_18" -> MR.strings.trans_SB_1_2_18
        "SB_1_3_28" -> MR.strings.trans_SB_1_3_28
        "SB_1_3_43" -> MR.strings.trans_SB_1_3_43
        "SB_1_5_11" -> MR.strings.trans_SB_1_5_11
        "SB_1_5_18" -> MR.strings.trans_SB_1_5_18
        "SB_1_7_6" -> MR.strings.trans_SB_1_7_6
        "SB_1_7_10" -> MR.strings.trans_SB_1_7_10
        "SB_1_8_26" -> MR.strings.trans_SB_1_8_26
        "SB_1_8_42" -> MR.strings.trans_SB_1_8_42
        "SB_1_13_10" -> MR.strings.trans_SB_1_13_10
        "SB_1_18_13" -> MR.strings.trans_SB_1_18_13
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }

fun getDescription(id: String) =
    when (id) {
        "SB_1_1_1" -> MR.strings.descr_SB_1_1_1
        "SB_1_1_10" -> MR.strings.descr_SB_1_1_10
        "SB_1_2_6" -> MR.strings.descr_SB_1_2_6
        "SB_1_2_8" -> MR.strings.descr_SB_1_2_8
        "SB_1_2_11" -> MR.strings.descr_SB_1_2_11
        "SB_1_2_16" -> MR.strings.descr_SB_1_2_16
        "SB_1_2_17" -> MR.strings.descr_SB_1_2_17
        "SB_1_2_18" -> MR.strings.descr_SB_1_2_18
        "SB_1_3_28" -> MR.strings.descr_SB_1_3_28
        "SB_1_3_43" -> MR.strings.descr_SB_1_3_43
        "SB_1_5_11" -> MR.strings.descr_SB_1_5_11
        "SB_1_5_18" -> MR.strings.descr_SB_1_5_18
        "SB_1_7_6" -> MR.strings.descr_SB_1_7_6
        "SB_1_7_10" -> MR.strings.descr_SB_1_7_10
        "SB_1_8_26" -> MR.strings.descr_SB_1_8_26
        "SB_1_8_42" -> MR.strings.descr_SB_1_8_42
        "SB_1_13_10" -> MR.strings.descr_SB_1_13_10
        "SB_1_18_13" -> MR.strings.descr_SB_1_18_13
        else -> throw IllegalArgumentException("unknown shloka id: $id")
    }
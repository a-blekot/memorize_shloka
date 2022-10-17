package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.ShlokaId.*
import com.a_blekot.shlokas.common.resources.MR.assets.BG_
import com.a_blekot.shlokas.common.resources.MR.assets.NI_
import com.a_blekot.shlokas.common.resources.MR.assets.SB_1
import com.a_blekot.shlokas.common.resources.MR.assets.SB_2
import com.a_blekot.shlokas.common.resources.MR.assets.SB_3
import com.a_blekot.shlokas.common.resources.MR.assets.SB_4
import com.a_blekot.shlokas.common.resources.MR.assets.SB_5
import com.a_blekot.shlokas.common.resources.MR.assets.SB_6
import com.a_blekot.shlokas.common.resources.MR.strings
import dev.icerock.moko.resources.AssetResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

class Verse(
    val title: StringResource,
    val sansk: StringResource,
    val words: StringResource,
    val trans: StringResource,
    val descr: StringResource,
    val file: AssetResource? = null
)

val versesMap =
    strings.run {
        mapOf(
            SB_1_1_1 to Verse(
                title_SB_1_1_1,
                sansk_SB_1_1_1,
                words_SB_1_1_1,
                trans_SB_1_1_1,
                descr_SB_1_1_1,
                SB_1.SB_1_1_1,
            ),
            SB_1_1_2 to Verse(
                title_SB_1_1_2,
                sansk_SB_1_1_2,
                words_SB_1_1_2,
                trans_SB_1_1_2,
                descr_SB_1_1_2,
                SB_1.SB_1_1_2,
            ),
            SB_1_1_3 to Verse(
                title_SB_1_1_3,
                sansk_SB_1_1_3,
                words_SB_1_1_3,
                trans_SB_1_1_3,
                descr_SB_1_1_3,
                SB_1.SB_1_1_3,
            ),
            SB_1_1_10 to Verse(
                title_SB_1_1_10,
                sansk_SB_1_1_10,
                words_SB_1_1_10,
                trans_SB_1_1_10,
                descr_SB_1_1_10,
                SB_1.SB_1_1_10,
            ),
            SB_1_2_6 to Verse(
                title_SB_1_2_6,
                sansk_SB_1_2_6,
                words_SB_1_2_6,
                trans_SB_1_2_6,
                descr_SB_1_2_6,
                SB_1.SB_1_2_6,
            ),
            SB_1_2_7 to Verse(
                title_SB_1_2_7,
                sansk_SB_1_2_7,
                words_SB_1_2_7,
                trans_SB_1_2_7,
                descr_SB_1_2_7,
                SB_1.SB_1_2_7,
            ),
            SB_1_2_8 to Verse(
                title_SB_1_2_8,
                sansk_SB_1_2_8,
                words_SB_1_2_8,
                trans_SB_1_2_8,
                descr_SB_1_2_8,
                SB_1.SB_1_2_8,
            ),
            SB_1_2_9 to Verse(
                title_SB_1_2_9,
                sansk_SB_1_2_9,
                words_SB_1_2_9,
                trans_SB_1_2_9,
                descr_SB_1_2_9,
                SB_1.SB_1_2_9,
            ),
            SB_1_2_10 to Verse(
                title_SB_1_2_10,
                sansk_SB_1_2_10,
                words_SB_1_2_10,
                trans_SB_1_2_10,
                descr_SB_1_2_10,
                SB_1.SB_1_2_10,
            ),
            SB_1_2_11 to Verse(
                title_SB_1_2_11,
                sansk_SB_1_2_11,
                words_SB_1_2_11,
                trans_SB_1_2_11,
                descr_SB_1_2_11,
                SB_1.SB_1_2_11,
            ),
            SB_1_2_12 to Verse(
                title_SB_1_2_12,
                sansk_SB_1_2_12,
                words_SB_1_2_12,
                trans_SB_1_2_12,
                descr_SB_1_2_12,
                SB_1.SB_1_2_12,
            ),
            SB_1_2_13 to Verse(
                title_SB_1_2_13,
                sansk_SB_1_2_13,
                words_SB_1_2_13,
                trans_SB_1_2_13,
                descr_SB_1_2_13,
                SB_1.SB_1_2_13,
            ),
            SB_1_2_14 to Verse(
                title_SB_1_2_14,
                sansk_SB_1_2_14,
                words_SB_1_2_14,
                trans_SB_1_2_14,
                descr_SB_1_2_14,
                SB_1.SB_1_2_14,
            ),
            SB_1_2_16 to Verse(
                title_SB_1_2_16,
                sansk_SB_1_2_16,
                words_SB_1_2_16,
                trans_SB_1_2_16,
                descr_SB_1_2_16,
                SB_1.SB_1_2_16,
            ),
            SB_1_2_17 to Verse(
                title_SB_1_2_17,
                sansk_SB_1_2_17,
                words_SB_1_2_17,
                trans_SB_1_2_17,
                descr_SB_1_2_17,
                SB_1.SB_1_2_17,
            ),
            SB_1_2_18 to Verse(
                title_SB_1_2_18,
                sansk_SB_1_2_18,
                words_SB_1_2_18,
                trans_SB_1_2_18,
                descr_SB_1_2_18,
                SB_1.SB_1_2_18,
            ),
            SB_1_2_19 to Verse(
                title_SB_1_2_19,
                sansk_SB_1_2_19,
                words_SB_1_2_19,
                trans_SB_1_2_19,
                descr_SB_1_2_19,
                SB_1.SB_1_2_19,
            ),
            SB_1_2_20 to Verse(
                title_SB_1_2_20,
                sansk_SB_1_2_20,
                words_SB_1_2_20,
                trans_SB_1_2_20,
                descr_SB_1_2_20,
                SB_1.SB_1_2_20,
            ),
            SB_1_2_21 to Verse(
                title_SB_1_2_21,
                sansk_SB_1_2_21,
                words_SB_1_2_21,
                trans_SB_1_2_21,
                descr_SB_1_2_21,
                SB_1.SB_1_2_21,
            ),
            SB_1_3_28 to Verse(
                title_SB_1_3_28,
                sansk_SB_1_3_28,
                words_SB_1_3_28,
                trans_SB_1_3_28,
                descr_SB_1_3_28,
                SB_1.SB_1_3_28,
            ),
            SB_1_3_43 to Verse(
                title_SB_1_3_43,
                sansk_SB_1_3_43,
                words_SB_1_3_43,
                trans_SB_1_3_43,
                descr_SB_1_3_43,
                SB_1.SB_1_3_43,
            ),
            SB_1_5_11 to Verse(
                title_SB_1_5_11,
                sansk_SB_1_5_11,
                words_SB_1_5_11,
                trans_SB_1_5_11,
                descr_SB_1_5_11,
                SB_1.SB_1_5_11,
            ),
            SB_1_5_17 to Verse(
                title_SB_1_5_17,
                sansk_SB_1_5_17,
                words_SB_1_5_17,
                trans_SB_1_5_17,
                descr_SB_1_5_17,
                SB_1.SB_1_5_17,
            ),
            SB_1_5_18 to Verse(
                title_SB_1_5_18,
                sansk_SB_1_5_18,
                words_SB_1_5_18,
                trans_SB_1_5_18,
                descr_SB_1_5_18,
                SB_1.SB_1_5_18,
            ),
            SB_1_7_6 to Verse(
                title_SB_1_7_6,
                sansk_SB_1_7_6,
                words_SB_1_7_6,
                trans_SB_1_7_6,
                descr_SB_1_7_6,
                SB_1.SB_1_7_6,
            ),
            SB_1_7_10 to Verse(
                title_SB_1_7_10,
                sansk_SB_1_7_10,
                words_SB_1_7_10,
                trans_SB_1_7_10,
                descr_SB_1_7_10,
                SB_1.SB_1_7_10,
            ),
            SB_1_8_18 to Verse(
                title_SB_1_8_18,
                sansk_SB_1_8_18,
                words_SB_1_8_18,
                trans_SB_1_8_18,
                descr_SB_1_8_18,
                SB_1.SB_1_8_18,
            ),
            SB_1_8_26 to Verse(
                title_SB_1_8_26,
                sansk_SB_1_8_26,
                words_SB_1_8_26,
                trans_SB_1_8_26,
                descr_SB_1_8_26,
                SB_1.SB_1_8_26,
            ),
            SB_1_8_42 to Verse(
                title_SB_1_8_42,
                sansk_SB_1_8_42,
                words_SB_1_8_42,
                trans_SB_1_8_42,
                descr_SB_1_8_42,
                SB_1.SB_1_8_42,
            ),
            SB_1_13_10 to Verse(
                title_SB_1_13_10,
                sansk_SB_1_13_10,
                words_SB_1_13_10,
                trans_SB_1_13_10,
                descr_SB_1_13_10,
                SB_1.SB_1_13_10,
            ),
            SB_1_18_13 to Verse(
                title_SB_1_18_13,
                sansk_SB_1_18_13,
                words_SB_1_18_13,
                trans_SB_1_18_13,
                descr_SB_1_18_13,
                SB_1.SB_1_18_13,
            ),
            SB_2_1_5 to Verse(
                title_SB_2_1_5,
                sansk_SB_2_1_5,
                words_SB_2_1_5,
                trans_SB_2_1_5,
                descr_SB_2_1_5,
                SB_2.SB_2_1_5,
            ),
            SB_2_1_6 to Verse(
                title_SB_2_1_6,
                sansk_SB_2_1_6,
                words_SB_2_1_6,
                trans_SB_2_1_6,
                descr_SB_2_1_6,
                SB_2.SB_2_1_6,
            ),
            SB_2_1_11 to Verse(
                title_SB_2_1_11,
                sansk_SB_2_1_11,
                words_SB_2_1_11,
                trans_SB_2_1_11,
                descr_SB_2_1_11,
                SB_2.SB_2_1_11,
            ),
            SB_2_3_10 to Verse(
                title_SB_2_3_10,
                sansk_SB_2_3_10,
                words_SB_2_3_10,
                trans_SB_2_3_10,
                descr_SB_2_3_10,
                SB_2.SB_2_3_10,
            ),
            SB_2_3_17 to Verse(
                title_SB_2_3_17,
                sansk_SB_2_3_17,
                words_SB_2_3_17,
                trans_SB_2_3_17,
                descr_SB_2_3_17,
                SB_2.SB_2_3_17,
            ),
            SB_2_3_19 to Verse(
                title_SB_2_3_19,
                sansk_SB_2_3_19,
                words_SB_2_3_19,
                trans_SB_2_3_19,
                descr_SB_2_3_19,
                SB_2.SB_2_3_19,
            ),
            SB_2_4_18 to Verse(
                title_SB_2_4_18,
                sansk_SB_2_4_18,
                words_SB_2_4_18,
                trans_SB_2_4_18,
                descr_SB_2_4_18,
                SB_2.SB_2_4_18,
            ),
            SB_2_9_33 to Verse(
                title_SB_2_9_33,
                sansk_SB_2_9_33,
                words_SB_2_9_33,
                trans_SB_2_9_33,
                descr_SB_2_9_33,
                SB_2.SB_2_9_33,
            ),
            SB_2_9_34 to Verse(
                title_SB_2_9_34,
                sansk_SB_2_9_34,
                words_SB_2_9_34,
                trans_SB_2_9_34,
                descr_SB_2_9_34,
                SB_2.SB_2_9_34,
            ),
            SB_2_9_35 to Verse(
                title_SB_2_9_35,
                sansk_SB_2_9_35,
                words_SB_2_9_35,
                trans_SB_2_9_35,
                descr_SB_2_9_35,
                SB_2.SB_2_9_35,
            ),
            SB_2_9_36 to Verse(
                title_SB_2_9_36,
                sansk_SB_2_9_36,
                words_SB_2_9_36,
                trans_SB_2_9_36,
                descr_SB_2_9_36,
                SB_2.SB_2_9_36,
            ),
            SB_2_10_1 to Verse(
                title_SB_2_10_1,
                sansk_SB_2_10_1,
                words_SB_2_10_1,
                trans_SB_2_10_1,
                descr_SB_2_10_1,
                SB_2.SB_2_10_1,
            ),
            SB_3_2_23 to Verse(
                title_SB_3_2_23,
                sansk_SB_3_2_23,
                words_SB_3_2_23,
                trans_SB_3_2_23,
                descr_SB_3_2_23,
//                SB_3.SB_3_2_23, TODO
            ),
            SB_3_5_41 to Verse(
                title_SB_3_5_41,
                sansk_SB_3_5_41,
                words_SB_3_5_41,
                trans_SB_3_5_41,
                descr_SB_3_5_41,
                SB_3.SB_3_5_41,
            ),
            SB_3_9_11 to Verse(
                title_SB_3_9_11,
                sansk_SB_3_9_11,
                words_SB_3_9_11,
                trans_SB_3_9_11,
                descr_SB_3_9_11,
                SB_3.SB_3_9_11,
            ),
            SB_3_25_19 to Verse(
                title_SB_3_25_19,
                sansk_SB_3_25_19,
                words_SB_3_25_19,
                trans_SB_3_25_19,
                descr_SB_3_25_19,
//                SB_3.SB_3_25_19, TODO
            ),
            SB_3_25_20 to Verse(
                title_SB_3_25_20,
                sansk_SB_3_25_20,
                words_SB_3_25_20,
                trans_SB_3_25_20,
                descr_SB_3_25_20,
//                SB_3.SB_3_25_20, TODO
            ),
            SB_3_25_21 to Verse(
                title_SB_3_25_21,
                sansk_SB_3_25_21,
                words_SB_3_25_21,
                trans_SB_3_25_21,
                descr_SB_3_25_21,
//                SB_3.SB_3_25_21, TODO
            ),
            SB_3_25_25 to Verse(
                title_SB_3_25_25,
                sansk_SB_3_25_25,
                words_SB_3_25_25,
                trans_SB_3_25_25,
                descr_SB_3_25_25,
//                SB_3.SB_3_25_25, TODO
            ),
            SB_3_29_11_12 to Verse(
                title_SB_3_29_11_12,
                sansk_SB_3_29_11_12,
                words_SB_3_29_11_12,
                trans_SB_3_29_11_12,
                descr_SB_3_29_11_12,
//                SB_3.SB_3_29_11_12, TODO
            ),
            SB_3_29_13 to Verse(
                title_SB_3_29_13,
                sansk_SB_3_29_13,
                words_SB_3_29_13,
                trans_SB_3_29_13,
                descr_SB_3_29_13,
//                SB_3.SB_3_29_13, TODO
            ),
            SB_3_31_1 to Verse(
                title_SB_3_31_1,
                sansk_SB_3_31_1,
                words_SB_3_31_1,
                trans_SB_3_31_1,
                descr_SB_3_31_1,
//                SB_3.SB_3_31_1, TODO
            ),
            SB_3_33_6 to Verse(
                title_SB_3_33_6,
                sansk_SB_3_33_6,
                words_SB_3_33_6,
                trans_SB_3_33_6,
                descr_SB_3_33_6,
//                SB_3.SB_3_33_6, TODO
            ),
            SB_3_33_7 to Verse(
                title_SB_3_33_7,
                sansk_SB_3_33_7,
                words_SB_3_33_7,
                trans_SB_3_33_7,
                descr_SB_3_33_7,
//                SB_3.SB_3_33_7, TODO
            ),
            SB_4_3_23 to Verse(
                title_SB_4_3_23,
                sansk_SB_4_3_23,
                words_SB_4_3_23,
                trans_SB_4_3_23,
                descr_SB_4_3_23,
//                SB_4.SB_4_3_23, TODO
            ),
            SB_4_30_35 to Verse(
                title_SB_4_30_35,
                sansk_SB_4_30_35,
                words_SB_4_30_35,
                trans_SB_4_30_35,
                descr_SB_4_30_35,
                SB_4.SB_4_30_35,
            ),
            SB_4_31_14 to Verse(
                title_SB_4_31_14,
                sansk_SB_4_31_14,
                words_SB_4_31_14,
                trans_SB_4_31_14,
                descr_SB_4_31_14,
                SB_4.SB_4_31_14,
            ),
            SB_5_5_1 to Verse(
                title_SB_5_5_1,
                sansk_SB_5_5_1,
                words_SB_5_5_1,
                trans_SB_5_5_1,
                descr_SB_5_5_1,
                SB_5.SB_5_5_1,
            ),
            SB_5_5_2 to Verse(
                title_SB_5_5_2,
                sansk_SB_5_5_2,
                words_SB_5_5_2,
                trans_SB_5_5_2,
                descr_SB_5_5_2,
                SB_5.SB_5_5_2,
            ),
            SB_5_5_4 to Verse(
                title_SB_5_5_4,
                sansk_SB_5_5_4,
                words_SB_5_5_4,
                trans_SB_5_5_4,
                descr_SB_5_5_4,
                SB_5.SB_5_5_4,
            ),
            SB_5_5_5 to Verse(
                title_SB_5_5_5,
                sansk_SB_5_5_5,
                words_SB_5_5_5,
                trans_SB_5_5_5,
                descr_SB_5_5_5,
//                SB_5.SB_5_5_5, TODO
            ),
            SB_5_5_8 to Verse(
                title_SB_5_5_8,
                sansk_SB_5_5_8,
                words_SB_5_5_8,
                trans_SB_5_5_8,
                descr_SB_5_5_8,
                SB_5.SB_5_5_8,
            ),
            SB_5_5_18 to Verse(
                title_SB_5_5_18,
                sansk_SB_5_5_18,
                words_SB_5_5_18,
                trans_SB_5_5_18,
                descr_SB_5_5_18,
                SB_5.SB_5_5_18,
            ),
            SB_5_12_12 to Verse(
                title_SB_5_12_12,
                sansk_SB_5_12_12,
                words_SB_5_12_12,
                trans_SB_5_12_12,
                descr_SB_5_12_12,
                SB_5.SB_5_12_12,
            ),
            SB_5_18_12 to Verse(
                title_SB_5_18_12,
                sansk_SB_5_18_12,
                words_SB_5_18_12,
                trans_SB_5_18_12,
                descr_SB_5_18_12,
                SB_5.SB_5_18_12,
            ),
            SB_6_1_13_14 to Verse(
                title_SB_6_1_13_14,
                sansk_SB_6_1_13_14,
                words_SB_6_1_13_14,
                trans_SB_6_1_13_14,
                descr_SB_6_1_13_14,
//                SB_6.SB_6_1_13_14, TODO
            ),
            SB_6_1_15 to Verse(
                title_SB_6_1_15,
                sansk_SB_6_1_15,
                words_SB_6_1_15,
                trans_SB_6_1_15,
                descr_SB_6_1_15,
                SB_6.SB_6_1_15,
            ),
            SB_6_1_40 to Verse(
                title_SB_6_1_40,
                sansk_SB_6_1_40,
                words_SB_6_1_40,
                trans_SB_6_1_40,
                descr_SB_6_1_40,
//                SB_6.SB_6_1_40, TODO
            ),
            SB_6_3_19 to Verse(
                title_SB_6_3_19,
                sansk_SB_6_3_19,
                words_SB_6_3_19,
                trans_SB_6_3_19,
                descr_SB_6_3_19,
                SB_6.SB_6_3_19,
            ),
            SB_6_3_20_21 to Verse(
                title_SB_6_3_20_21,
                sansk_SB_6_3_20_21,
                words_SB_6_3_20_21,
                trans_SB_6_3_20_21,
                descr_SB_6_3_20_21,
//                SB_6.SB_6_3_20_21, TODO
            ),
            SB_6_3_31 to Verse(
                title_SB_6_3_31,
                sansk_SB_6_3_31,
                words_SB_6_3_31,
                trans_SB_6_3_31,
                descr_SB_6_3_31,
                SB_6.SB_6_3_31,
            ),
            SB_6_14_5 to Verse(
                title_SB_6_14_5,
                sansk_SB_6_14_5,
                words_SB_6_14_5,
                trans_SB_6_14_5,
                descr_SB_6_14_5,
                SB_6.SB_6_14_5,
            ),
            SB_6_17_28 to Verse(
                title_SB_6_17_28,
                sansk_SB_6_17_28,
                words_SB_6_17_28,
                trans_SB_6_17_28,
                descr_SB_6_17_28,
                SB_6.SB_6_17_28,
            ),
            NI_01 to Verse(
                title_NI_01,
                sansk_NI_01,
                words_NI_01,
                trans_NI_01,
                descr_NI_01,
                NI_.NI_01
            ),
            NI_02 to Verse(
                title_NI_02,
                sansk_NI_02,
                words_NI_02,
                trans_NI_02,
                descr_NI_02,
                NI_.NI_02
            ),
            NI_03 to Verse(
                title_NI_03,
                sansk_NI_03,
                words_NI_03,
                trans_NI_03,
                descr_NI_03,
                NI_.NI_03
            ),
            NI_04 to Verse(
                title_NI_04,
                sansk_NI_04,
                words_NI_04,
                trans_NI_04,
                descr_NI_04,
                NI_.NI_04
            ),
            NI_05 to Verse(
                title_NI_05,
                sansk_NI_05,
                words_NI_05,
                trans_NI_05,
                descr_NI_05,
                NI_.NI_05
            ),
            NI_06 to Verse(
                title_NI_06,
                sansk_NI_06,
                words_NI_06,
                trans_NI_06,
                descr_NI_06,
                NI_.NI_06
            ),
            NI_07 to Verse(
                title_NI_07,
                sansk_NI_07,
                words_NI_07,
                trans_NI_07,
                descr_NI_07,
                NI_.NI_07
            ),
            NI_08 to Verse(
                title_NI_08,
                sansk_NI_08,
                words_NI_08,
                trans_NI_08,
                descr_NI_08,
                NI_.NI_08
            ),
            NI_09 to Verse(
                title_NI_09,
                sansk_NI_09,
                words_NI_09,
                trans_NI_09,
                descr_NI_09,
                NI_.NI_09
            ),
            NI_10 to Verse(
                title_NI_10,
                sansk_NI_10,
                words_NI_10,
                trans_NI_10,
                descr_NI_10,
                NI_.NI_10
            ),
            NI_11 to Verse(
                title_NI_11,
                sansk_NI_11,
                words_NI_11,
                trans_NI_11,
                descr_NI_11,
                NI_.NI_11
            ),
            BG_1_1 to Verse(
                title_BG_1_1,
                sansk_BG_1_1,
                words_BG_1_1,
                trans_BG_1_1,
                descr_BG_1_1,
                BG_.BG_1_1
            ),
            BG_2_7 to Verse(
                title_BG_2_7,
                sansk_BG_2_7,
                words_BG_2_7,
                trans_BG_2_7,
                descr_BG_2_7,
                BG_.BG_2_7
            ),
            BG_2_13 to Verse(
                title_BG_2_13,
                sansk_BG_2_13,
                words_BG_2_13,
                trans_BG_2_13,
                descr_BG_2_13,
                BG_.BG_2_13
            ),
            BG_2_27 to Verse(
                title_BG_2_27,
                sansk_BG_2_27,
                words_BG_2_27,
                trans_BG_2_27,
                descr_BG_2_27,
                BG_.BG_2_27
            ),
            BG_3_27 to Verse(
                title_BG_3_27,
                sansk_BG_3_27,
                words_BG_3_27,
                trans_BG_3_27,
                descr_BG_3_27,
                BG_.BG_3_27
            ),
            BG_4_2 to Verse(
                title_BG_4_2,
                sansk_BG_4_2,
                words_BG_4_2,
                trans_BG_4_2,
                descr_BG_4_2,
                BG_.BG_4_2
            ),
            BG_4_8 to Verse(
                title_BG_4_8,
                sansk_BG_4_8,
                words_BG_4_8,
                trans_BG_4_8,
                descr_BG_4_8,
                BG_.BG_4_8
            ),
            BG_4_9 to Verse(
                title_BG_4_9,
                sansk_BG_4_9,
                words_BG_4_9,
                trans_BG_4_9,
                descr_BG_4_9,
                BG_.BG_4_9
            ),
            BG_4_34 to Verse(
                title_BG_4_34,
                sansk_BG_4_34,
                words_BG_4_34,
                trans_BG_4_34,
                descr_BG_4_34,
                BG_.BG_4_34
            ),
            BG_5_22 to Verse(
                title_BG_5_22,
                sansk_BG_5_22,
                words_BG_5_22,
                trans_BG_5_22,
                descr_BG_5_22,
                BG_.BG_5_22
            ),
            BG_5_29 to Verse(
                title_BG_5_29,
                sansk_BG_5_29,
                words_BG_5_29,
                trans_BG_5_29,
                descr_BG_5_29,
                BG_.BG_5_29
            ),
            BG_6_47 to Verse(
                title_BG_6_47,
                sansk_BG_6_47,
                words_BG_6_47,
                trans_BG_6_47,
                descr_BG_6_47,
                BG_.BG_6_47
            ),
            BG_7_14 to Verse(
                title_BG_7_14,
                sansk_BG_7_14,
                words_BG_7_14,
                trans_BG_7_14,
                descr_BG_7_14,
                BG_.BG_7_14
            ),
            BG_8_5 to Verse(
                title_BG_8_5,
                sansk_BG_8_5,
                words_BG_8_5,
                trans_BG_8_5,
                descr_BG_8_5,
                BG_.BG_8_5
            ),
            BG_9_2 to Verse(
                title_BG_9_2,
                sansk_BG_9_2,
                words_BG_9_2,
                trans_BG_9_2,
                descr_BG_9_2,
                BG_.BG_9_2
            ),
            BG_9_14 to Verse(
                title_BG_9_14,
                sansk_BG_9_14,
                words_BG_9_14,
                trans_BG_9_14,
                descr_BG_9_14,
                BG_.BG_9_14
            ),
            BG_9_26 to Verse(
                title_BG_9_26,
                sansk_BG_9_26,
                words_BG_9_26,
                trans_BG_9_26,
                descr_BG_9_26,
                BG_.BG_9_26
            ),
            BG_9_27 to Verse(
                title_BG_9_27,
                sansk_BG_9_27,
                words_BG_9_27,
                trans_BG_9_27,
                descr_BG_9_27,
                BG_.BG_9_27
            ),
            BG_10_8 to Verse(
                title_BG_10_8,
                sansk_BG_10_8,
                words_BG_10_8,
                trans_BG_10_8,
                descr_BG_10_8,
                BG_.BG_10_8
            ),
            BG_10_9 to Verse(
                title_BG_10_9,
                sansk_BG_10_9,
                words_BG_10_9,
                trans_BG_10_9,
                descr_BG_10_9,
                BG_.BG_10_9
            ),
            BG_10_10 to Verse(
                title_BG_10_10,
                sansk_BG_10_10,
                words_BG_10_10,
                trans_BG_10_10,
                descr_BG_10_10,
                BG_.BG_10_10
            ),
            BG_10_11 to Verse(
                title_BG_10_11,
                sansk_BG_10_11,
                words_BG_10_11,
                trans_BG_10_11,
                descr_BG_10_11,
                BG_.BG_10_11
            ),
            BG_13_22 to Verse(
                title_BG_13_22,
                sansk_BG_13_22,
                words_BG_13_22,
                trans_BG_13_22,
                descr_BG_13_22,
                BG_.BG_13_22
            ),
            BG_14_4 to Verse(
                title_BG_14_4,
                sansk_BG_14_4,
                words_BG_14_4,
                trans_BG_14_4,
                descr_BG_14_4,
                BG_.BG_14_4
            ),
            BG_14_26 to Verse(
                title_BG_14_26,
                sansk_BG_14_26,
                words_BG_14_26,
                trans_BG_14_26,
                descr_BG_14_26,
                BG_.BG_14_26
            ),
            BG_15_7 to Verse(
                title_BG_15_7,
                sansk_BG_15_7,
                words_BG_15_7,
                trans_BG_15_7,
                descr_BG_15_7,
                BG_.BG_15_7
            ),
            BG_15_15 to Verse(
                title_BG_15_15,
                sansk_BG_15_15,
                words_BG_15_15,
                trans_BG_15_15,
                descr_BG_15_15,
                BG_.BG_15_15
            ),
            BG_18_54 to Verse(
                title_BG_18_54,
                sansk_BG_18_54,
                words_BG_18_54,
                trans_BG_18_54,
                descr_BG_18_54,
                BG_.BG_18_54
            ),
            BG_18_55 to Verse(
                title_BG_18_55,
                sansk_BG_18_55,
                words_BG_18_55,
                trans_BG_18_55,
                descr_BG_18_55,
                BG_.BG_18_55
            ),
            BG_18_61 to Verse(
                title_BG_18_61,
                sansk_BG_18_61,
                words_BG_18_61,
                trans_BG_18_61,
                descr_BG_18_61,
                BG_.BG_18_61
            ),
            BG_18_65 to Verse(
                title_BG_18_65,
                sansk_BG_18_65,
                words_BG_18_65,
                trans_BG_18_65,
                descr_BG_18_65,
                BG_.BG_18_65
            ),
            BG_18_66 to Verse(
                title_BG_18_66,
                sansk_BG_18_66,
                words_BG_18_66,
                trans_BG_18_66,
                descr_BG_18_66,
                BG_.BG_18_66
            ),
        )
    }

//    else -> throw IllegalArgumentException(unknown shloka id: $id)

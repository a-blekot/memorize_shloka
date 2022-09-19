package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.resources.MR
import com.a_blekot.shlokas.common.resources.MR.strings.title_NI
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_1_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_2_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_3_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_4_5_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_6_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_1_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_2_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_3_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_4_5_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_6_canto
import dev.icerock.moko.resources.StringResource

class Title(
    val title: StringResource,
    val shortTitle: StringResource,
)

val listsMap = mapOf(
    "sb_1_canto_config" to Title(title_SB_1_canto, title_short_SB_1_canto),
    "sb_2_canto_config" to Title(title_SB_2_canto, title_short_SB_2_canto),
    "sb_3_canto_config" to Title(title_SB_3_canto, title_short_SB_3_canto),
    "sb_4_5_canto_config" to Title(title_SB_4_5_canto, title_short_SB_4_5_canto),
    "sb_6_canto_config" to Title(title_SB_6_canto, title_short_SB_6_canto),
    "ni_config" to Title(title_NI, title_NI),
)

package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.ListId.*
import com.a_blekot.shlokas.common.resources.MR.strings.title_BG
import com.a_blekot.shlokas.common.resources.MR.strings.title_ISO
import com.a_blekot.shlokas.common.resources.MR.strings.title_NI
import com.a_blekot.shlokas.common.resources.MR.strings.title_NOD
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_1_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_2_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_3_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_4_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_5_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_SB_6_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_1_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_2_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_3_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_4_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_5_canto
import com.a_blekot.shlokas.common.resources.MR.strings.title_short_SB_6_canto
import dev.icerock.moko.resources.StringResource

class Title(
    val title: StringResource,
    val shortTitle: StringResource,
)

val listsMap = mapOf(
    BG to Title(title_BG, title_BG),
    NI to Title(title_NI, title_NI),
    ISO to Title(title_ISO, title_ISO),
    NOD to Title(title_NOD, title_NOD),
    SB_1 to Title(title_SB_1_canto, title_short_SB_1_canto),
    SB_2 to Title(title_SB_2_canto, title_short_SB_2_canto),
    SB_3 to Title(title_SB_3_canto, title_short_SB_3_canto),
    SB_4 to Title(title_SB_4_canto, title_short_SB_4_canto),
    SB_5 to Title(title_SB_5_canto, title_short_SB_5_canto),
    SB_6 to Title(title_SB_6_canto, title_short_SB_6_canto),
)

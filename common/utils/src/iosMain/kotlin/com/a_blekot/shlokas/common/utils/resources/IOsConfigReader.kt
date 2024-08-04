package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.resources.MR.files.BG_json
import com.a_blekot.shlokas.common.resources.MR.files.ISO_json
import com.a_blekot.shlokas.common.resources.MR.files.NI_json
import com.a_blekot.shlokas.common.resources.MR.files.NK_json
import com.a_blekot.shlokas.common.resources.MR.files.NOD_json
import com.a_blekot.shlokas.common.resources.MR.files.SB_1_json
import com.a_blekot.shlokas.common.resources.MR.files.SB_2_json
import com.a_blekot.shlokas.common.resources.MR.files.SB_3_json
import com.a_blekot.shlokas.common.resources.MR.files.SB_4_json
import com.a_blekot.shlokas.common.resources.MR.files.SB_5_json
import com.a_blekot.shlokas.common.resources.MR.files.SB_6_json
import dev.icerock.moko.resources.FileResource

class IOsConfigReader: ConfigReader {

    override fun readConfig(type: ListId): String =
        when (type) {
            ListId.BG -> readFileResource(BG_json)
            ListId.ISO -> readFileResource(ISO_json)
            ListId.NI -> readFileResource(NI_json)
            ListId.NK -> readFileResource(NK_json)
            ListId.NOD -> readFileResource(NOD_json)
            ListId.SB_1 -> readFileResource(SB_1_json)
            ListId.SB_2 -> readFileResource(SB_2_json)
            ListId.SB_3 -> readFileResource(SB_3_json)
            ListId.SB_4 -> readFileResource(SB_4_json)
            ListId.SB_5 -> readFileResource(SB_5_json)
            ListId.SB_6 -> readFileResource(SB_6_json)
            else -> throw IllegalArgumentException("unknown config: $type")
        }

    private fun readFileResource(fileResource: FileResource) =
        fileResource.readText()
}

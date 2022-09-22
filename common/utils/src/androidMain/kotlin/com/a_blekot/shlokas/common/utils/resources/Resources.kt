package com.a_blekot.shlokas.common.utils.resources

import android.content.Context
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.resources.MR.files.NI
import com.a_blekot.shlokas.common.resources.MR.files.SB_1
import com.a_blekot.shlokas.common.resources.MR.files.SB_2
import com.a_blekot.shlokas.common.resources.MR.files.SB_3
import com.a_blekot.shlokas.common.resources.MR.files.SB_4
import com.a_blekot.shlokas.common.resources.MR.files.SB_5
import com.a_blekot.shlokas.common.resources.MR.files.SB_6
import dev.icerock.moko.resources.FileResource

class AndroidConfigReader(private val context: Context): ConfigReader {
    override fun readConfig(type: ListId): String =
        when (type) {
            ListId.SB_1 -> readFileResource(SB_1)
            ListId.SB_2 -> readFileResource(SB_2)
            ListId.SB_3 -> readFileResource(SB_3)
            ListId.SB_4 -> readFileResource(SB_4)
            ListId.SB_5 -> readFileResource(SB_5)
            ListId.SB_6 -> readFileResource(SB_6)
            ListId.NI -> readFileResource(NI)
            else -> throw IllegalArgumentException("unknown config: $type")
        }

    private fun readFileResource(fileResource: FileResource) =
        fileResource.readText(context)
}

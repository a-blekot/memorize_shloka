package com.a_blekot.shlokas.common.utils.resources

import android.content.Context
import com.a_blekot.shlokas.common.resources.MR.files.ni_config
import com.a_blekot.shlokas.common.resources.MR.files.sb_1_canto_config
import dev.icerock.moko.resources.FileResource

class AndroidConfigReader(private val context: Context): ConfigReader {
    override fun readConfig(configName: String): String =
        when (configName) {
            "sb_1_canto_config" -> readFileResource(sb_1_canto_config)
            "ni_config" -> readFileResource(ni_config)
            else -> throw IllegalArgumentException("unknown config: $configName")
        }

    private fun readFileResource(fileResource: FileResource) =
        fileResource.readText(context)
}

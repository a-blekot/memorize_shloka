package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.resources.MR.assets.ni
import com.a_blekot.shlokas.common.resources.MR.assets.sb_1_canto

interface ConfigReader {
    fun readConfig(configName: String): String
}

fun getAsset(folder: String, fileName: String) =
    when (folder) {
        "sb_1_canto" -> getFileFromSbFirstCanto(fileName).originalPath
        "ni" -> getFileFromNI(fileName).originalPath
        else -> throw IllegalArgumentException("unknown folder: $folder")
    }

private fun getFileFromSbFirstCanto(fileName: String) =
    when (fileName) {
        "SB_1_1_1" -> sb_1_canto.SB_1_1_1
        "SB_1_1_10" -> sb_1_canto.SB_1_1_10
        "SB_1_2_6" -> sb_1_canto.SB_1_2_6
        "SB_1_2_8" -> sb_1_canto.SB_1_2_8
        "SB_1_2_11" -> sb_1_canto.SB_1_2_11
        "SB_1_2_16" -> sb_1_canto.SB_1_2_16
        "SB_1_2_17" -> sb_1_canto.SB_1_2_17
        "SB_1_2_18" -> sb_1_canto.SB_1_2_18
        "SB_1_3_28" -> sb_1_canto.SB_1_3_28
        "SB_1_3_43" -> sb_1_canto.SB_1_3_43
        "SB_1_5_11" -> sb_1_canto.SB_1_5_11
        "SB_1_5_18" -> sb_1_canto.SB_1_5_18
        "SB_1_7_6" -> sb_1_canto.SB_1_7_6
        "SB_1_7_10" -> sb_1_canto.SB_1_7_10
        "SB_1_8_26" -> sb_1_canto.SB_1_8_26
        "SB_1_8_42" -> sb_1_canto.SB_1_8_42
        "SB_1_13_10" -> sb_1_canto.SB_1_13_10
        "SB_1_18_13" -> sb_1_canto.SB_1_18_13
        else -> throw IllegalArgumentException("unknown file name: $fileName")
    }

private fun getFileFromNI(fileName: String) =
    when (fileName) {
        "NI_01" -> ni.NI_01
        "NI_02" -> ni.NI_02
        "NI_03" -> ni.NI_03
        "NI_04" -> ni.NI_04
        "NI_05" -> ni.NI_05
        "NI_06" -> ni.NI_06
        "NI_07" -> ni.NI_07
        "NI_08" -> ni.NI_08
        "NI_09" -> ni.NI_09
        "NI_10" -> ni.NI_10
        "NI_11" -> ni.NI_11
        else -> throw IllegalArgumentException("unknown file name: $fileName")
    }

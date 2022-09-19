package com.a_blekot.shlokas.common.utils.resources

interface ConfigReader {
    fun readConfig(configName: String): String
}

fun getAsset(id: String) =
    versesMap[id]?.run {
        file?.originalPath
    } ?: throw IllegalArgumentException("unknown shloka id: $id")

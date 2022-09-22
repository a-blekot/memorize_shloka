package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.ListId

interface ConfigReader {
    fun readConfig(type: ListId): String
}

fun getAsset(id: String) =
    versesMap[id]?.run {
        file?.originalPath
    } ?: throw IllegalArgumentException("unknown shloka id: $id")

package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaId

interface ConfigReader {
    fun readConfig(type: ListId): String
}

fun getAsset(id: ShlokaId) =
    versesMap[id]?.run {
        file?.originalPath
    } ?: throw IllegalArgumentException("unknown shloka id: $id")

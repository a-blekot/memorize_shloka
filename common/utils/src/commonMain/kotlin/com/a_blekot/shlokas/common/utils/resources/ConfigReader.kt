package com.a_blekot.shlokas.common.utils.resources

import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.data.ShlokaId

interface ConfigReader {
    fun readConfig(type: ListId): String
}

fun getAssetPath(id: ShlokaId) =
    versesMap[id]?.file?.originalPath
        ?: throw IllegalArgumentException("unknown shloka id: $id")

fun getAsset(id: ShlokaId) =
    versesMap[id]?.file
        ?: throw IllegalArgumentException("unknown shloka id: $id")


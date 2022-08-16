package com.a_blekot.shlokas.common.utils

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.utils.resources.ConfigReader
import io.github.aakira.napier.Napier
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json {
    prettyPrint = true
    encodeDefaults = true
    ignoreUnknownKeys = true
}

interface Filer {
    fun write(filePath: String, text: String)
    fun read(filePath: String): String
    fun rename(old: String, new: String): Boolean
}

fun writeToFile(config: ListConfig, filer: Filer): Boolean {
    filer.write(config.fileName, json.encodeToString(config))
    return true
}

fun readConfig(configName: String, configReader: ConfigReader): ListConfig? =
    try {
        val text = configReader.readConfig(configName)
        if (text.isBlank()) {
            null
        } else {
            json.decodeFromString<ListConfig>(text)
        }
    } catch (e: Throwable) {
        Napier.e("config not found $configName")
        null
    }

fun readFromFile(filePath: String, filer: Filer): ListConfig? {
    val text = filer.read(filePath)
    if (text.isBlank()) {
        return null
    }

    return try {
        json.decodeFromString<ListConfig>(text)
    } catch (e: Throwable) {
        null
    }
}

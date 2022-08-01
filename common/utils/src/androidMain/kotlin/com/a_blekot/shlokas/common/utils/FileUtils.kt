package com.a_blekot.shlokas.common.utils

import android.content.Context
import java.io.File

class AndroidFiler(context: Context) : Filer {

    private val configs = File(context.externalCacheDir, "configs")
    private val illegalCharacters =
        charArrayOf('/', '\n', '\r', '\t', '\u0000', '\u000c', '`', '?', '*', '\\', '<', '>', '|', '\"', ':')

    override fun write(filePath: String, text: String) {
        if (!configs.exists()) {
            configs.mkdir()
        }

        File(configs, filePath).writeText(text)
    }
//    /document/9016-4EF8:Music/Bhaktivaibhava/SB 1.1.1.mp3
//    /storage/emulated/0/Android/data/com.a_blekot.shlokas.dev/cache/configs/Shlokas 889.json
    override fun read(filePath: String): String {
        if (filePath.isBlank()) {
            return ""
        }

        val file = File(configs, filePath)
        if (!file.isFile || !file.exists()) {
            return ""
        }

        return file.readText()
    }

    override fun rename(old: String, new: String): Boolean {
        if (fileNameIsWrong(new)) {
            return false
        }

        val file = File(configs, old)
        val newFile = File(configs, new)
        if (!file.exists() || newFile.exists()) {
            return false
        }

        file.renameTo(newFile)
        return true
    }

    private fun fileNameIsWrong(fileName: String) =
        fileName.isBlank() || fileName.any { illegalCharacters.contains(it) }
}
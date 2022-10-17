package com.a_blekot.shlokas.common.utils


class IOsFiler : Filer {

    override fun write(filePath: String, text: String) {}
    override fun read(filePath: String) = ""

    override fun rename(old: String, new: String) = true
}
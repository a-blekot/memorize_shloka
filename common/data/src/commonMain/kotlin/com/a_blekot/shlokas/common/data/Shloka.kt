package com.a_blekot.shlokas.common.data

data class Shloka(
    val id: Long,
    val title: String,
    val fileName: String,
    val description: String = "",
)

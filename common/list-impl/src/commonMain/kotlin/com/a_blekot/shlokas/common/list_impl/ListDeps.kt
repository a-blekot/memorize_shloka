package com.a_blekot.shlokas.common.list_impl

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.utils.ConfigReader
import com.a_blekot.shlokas.common.utils.DispatcherProvider
import com.a_blekot.shlokas.common.utils.Filer

data class ListDeps(
    var config: ListConfig,
    val filer: Filer,
    val configReader: ConfigReader,
    val dispatchers: DispatcherProvider,
)

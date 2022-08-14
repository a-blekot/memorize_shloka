package com.a_blekot.shlokas.common.list_impl

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.utils.resources.ConfigReader
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import com.a_blekot.shlokas.common.utils.Filer
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler

data class ListDeps(
    var config: ListConfig,
    val filer: Filer,
    val configReader: ConfigReader,
    val stringResourceHandler: StringResourceHandler,
    val dispatchers: DispatcherProvider,
)

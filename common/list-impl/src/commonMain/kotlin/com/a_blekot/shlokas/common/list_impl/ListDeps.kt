package com.a_blekot.shlokas.common.list_impl

import com.a_blekot.shlokas.common.data.ListConfig
import com.a_blekot.shlokas.common.data.PlatformApi
import com.a_blekot.shlokas.common.utils.analytics.Analytics
import com.a_blekot.shlokas.common.utils.resources.ConfigReader
import com.a_blekot.shlokas.common.utils.dispatchers.DispatcherProvider
import com.a_blekot.shlokas.common.utils.Filer
import com.a_blekot.shlokas.common.utils.resources.StringResourceHandler

data class ListDeps(
    val filer: Filer,
    var config: ListConfig,
    val analytics: Analytics,
    val platformApi: PlatformApi,
    val dispatchers: DispatcherProvider,
    val configReader: ConfigReader,
    val stringResourceHandler: StringResourceHandler,
)

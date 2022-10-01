package com.a_blekot.shlokas.common.utils

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.statekeeper.StateKeeper


inline fun <reified T: Parcelable> StateKeeper.consume(key: String): T? =
    consume(key, T::class)
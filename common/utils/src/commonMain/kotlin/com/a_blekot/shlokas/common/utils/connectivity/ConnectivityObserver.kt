package com.a_blekot.shlokas.common.utils.connectivity

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    @Parcelize
    enum class Status: Parcelable {
        Available,
        Unavailable,
        Losing,
        Lost
    }
}
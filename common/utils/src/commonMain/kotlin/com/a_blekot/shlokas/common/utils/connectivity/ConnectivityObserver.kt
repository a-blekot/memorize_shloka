package com.a_blekot.shlokas.common.utils.connectivity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ConnectivityObserver {

    fun observe(): Flow<Boolean>
    fun start() {}
    fun stop() {}
}

class ConnectivityObserverStub : ConnectivityObserver {
    override fun observe() = flowOf(true)
    override fun start() {}
    override fun stop() {}
}

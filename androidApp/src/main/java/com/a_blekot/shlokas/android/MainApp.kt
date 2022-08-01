package com.a_blekot.shlokas.android

import android.app.Application
import com.a_blekot.shlokas.common.player_api.PlayerBus
import com.a_blekot.shlokas.common.player_impl.PlayerBusImpl
import com.a_blekot.shlokas.common.utils.dispatchers
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainApp : Application() {
    companion object {
        lateinit var app: MainApp
    }

    lateinit var playerBus: PlayerBus

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        app = this
        playerBus = PlayerBusImpl(dispatchers())
    }
}
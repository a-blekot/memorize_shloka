package com.a_blekot.shlokas.common.utils.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver.Status
import com.a_blekot.shlokas.common.utils.connectivity.ConnectivityObserver.Status.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ConnectivityObserverAndroid(private val context: Context) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<Status> {
        return callbackFlow {

            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            val callback = networkCallback {
                launch {
                    send(it)
                }
            }

            connectivityManager.registerNetworkCallback(networkRequest, callback)

            val currentState = getCurrentConnectivityState(connectivityManager)
            trySend(currentState)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

    private fun networkCallback(callback: (Status) -> Unit): ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) = callback.invoke(Available)
            override fun onLosing(network: Network, maxMsToLive: Int) = callback.invoke(Losing)
            override fun onLost(network: Network) = callback.invoke(Lost)
            override fun onUnavailable() = callback.invoke(Unavailable)
        }

    private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager) =
        connectivityManager.allNetworks.any { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
        }.toConnectionState()

    private fun Boolean.toConnectionState() = if (this) Available else Unavailable
}
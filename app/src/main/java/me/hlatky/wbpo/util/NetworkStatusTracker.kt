package me.hlatky.wbpo.util

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkStatusTracker(private val manager: ConnectivityManager) {

    fun observe(): Flow<Status> = callbackFlow {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                trySend(Status.Unavailable)
            }

            override fun onAvailable(network: Network) {
                trySend(Status.Available)
            }

            override fun onLost(network: Network) {
                trySend(Status.Unavailable)
            }
        }

        manager.registerDefaultNetworkCallback(networkStatusCallback)

        awaitClose {
            manager.unregisterNetworkCallback(networkStatusCallback)
        }
    }.distinctUntilChanged()

    sealed class Status {
        object Available : Status()
        object Unavailable : Status()
    }
}

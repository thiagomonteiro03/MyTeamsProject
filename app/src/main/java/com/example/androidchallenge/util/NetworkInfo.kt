package com.example.androidchallenge.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

open class NetworkInfo(private val context: Context, autoStart: Boolean = true) : NetworkCallback() {

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    private val networkRequest: NetworkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            .build()
    }
    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    protected val _networkState: MutableSharedFlow<Boolean> = MutableSharedFlow(replay = 1)
    val networkState: SharedFlow<Boolean>
        get() = _networkState

    init {
        if (autoStart) {
            connectivityManager.requestNetwork(networkRequest, this)
            runBlocking { _networkState.emit(isNetworkAvailable()) }
        }
    }

    open fun isNetworkAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT < 23) {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.isConnected ?: false
        } else {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            networkCapabilities?.hasNetworkTransport() ?: false
        }
    }

    private fun NetworkCapabilities.hasNetworkTransport() =
        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(NetworkCapabilities.TRANSPORT_VPN)

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        scope.launch { _networkState.emit(true) }
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        scope.launch { _networkState.emit(false) }
    }

    override fun onUnavailable() {
        super.onUnavailable()

        scope.launch { _networkState.emit(false) }
    }

}
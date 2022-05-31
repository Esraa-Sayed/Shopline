package com.eCommerce.shopify.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import androidx.annotation.WorkerThread
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData

class NetworkConnectionLiveData(private val context: Context) : LiveData<Boolean>() {
    private val connectivityManager: ConnectivityManager = context.getSystemService()!!
    private lateinit var networkReceiver: BroadcastReceiver
    private lateinit var connectivityManagerCallback: NetworkCallback

    override fun setValue(value: Boolean?) {
        if (getValue() == value)
            return
        super.setValue(value)
    }

    override fun onActive() {
        super.onActive()
        updateConnection()
        connectivityManagerCallback = object : NetworkCallback() {
            @WorkerThread
            override fun onCapabilitiesChanged(network: Network,
                                               networkCapabilities: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                if (networkCapabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    postValue(true)
                }
            }

            @WorkerThread
            override fun onLost(network: Network) {
                postValue(false)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    @Suppress("DEPRECATION")
    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        value = activeNetwork?.isConnected == true
    }
}
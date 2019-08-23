package com.scotia.codingchallenge.utils

import android.content.Context
import android.net.ConnectivityManager

object CheckInternetConnectivity {
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
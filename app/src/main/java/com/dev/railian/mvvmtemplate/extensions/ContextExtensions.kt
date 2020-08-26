package com.dev.railian.mvvmtemplate.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isInternetExist(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}
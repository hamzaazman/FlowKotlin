package com.hamzaazman.flowexample.common

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hamzaazman.flowexample.common.Constants.CHECK_NETWORK_ACTION
import com.hamzaazman.flowexample.common.Constants.CHECK_NETWORK_WARNING
import dagger.hilt.android.internal.Contexts.getApplication


fun hasInternetConnection(application: Application): Boolean {
    val connectivityManager =
        getApplication(application).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun connectivitySnackBar(
    view: View,
    action: View.OnClickListener
) =
    Snackbar.make(
        view,
        CHECK_NETWORK_WARNING,
        Snackbar.LENGTH_INDEFINITE
    ).setAction(CHECK_NETWORK_ACTION, action)
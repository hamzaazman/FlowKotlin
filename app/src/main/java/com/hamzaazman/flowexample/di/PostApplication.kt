package com.hamzaazman.flowexample.di

import android.app.Application
import com.hamzaazman.flowexample.common.hasInternetConnection
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PostApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        hasInternetConnection(this@PostApplication).let { result ->
            networkResult = result
        }
    }

    companion object {
        var networkResult = false
    }
}
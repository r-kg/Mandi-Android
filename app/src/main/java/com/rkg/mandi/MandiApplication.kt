package com.rkg.mandi

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MandiApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        // Do Initialize on application level
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}
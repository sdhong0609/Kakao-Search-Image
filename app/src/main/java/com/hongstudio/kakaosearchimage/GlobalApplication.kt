package com.hongstudio.kakaosearchimage

import android.app.Application
import timber.log.Timber

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

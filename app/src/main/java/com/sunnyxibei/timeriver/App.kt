package com.sunnyxibei.timeriver

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(koinModule)
        }
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var appContext: App
    }
}
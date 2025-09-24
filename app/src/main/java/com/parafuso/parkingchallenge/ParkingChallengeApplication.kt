package com.parafuso.parkingchallenge

import android.app.Application
import com.parafuso.parkingchallenge.core.di.loadCoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import timber.log.Timber

class ParkingChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@ParkingChallengeApplication)
            modules(getKoinModules())
        }
    }

    private fun getKoinModules() = mutableListOf<Module>().apply {
        addAll(loadCoreModule())
    }
}
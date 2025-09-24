package com.parafuso.parkingchallenge

import android.app.Application
import com.parafuso.parkingchallenge.core.di.loadCoreModule
import com.parafuso.parkingchallenge.feature.parking.di.loadParkingModule
import com.parafuso.parkingchallenge.feature.parkingout.di.loadParkingOutModule
import com.parafuso.parkingchallenge.feature.parkingpay.di.loadParkingPayModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
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

    private fun getKoinModules() = listOf(
        loadCoreModule(),
        loadParkingModule(),
        loadParkingOutModule(),
        loadParkingPayModule(),
    )
}
package com.parafuso.parkingchallenge.feature.parkingpay.di

import com.parafuso.parkingchallenge.core.data.remote.HttpClient
import com.parafuso.parkingchallenge.feature.parkingpay.data.api.ParkingPayService
import com.parafuso.parkingchallenge.feature.parkingpay.data.datasource.ParkingPayRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parkingpay.data.repository.ParkingPayRepositoryImpl
import com.parafuso.parkingchallenge.feature.parkingpay.domain.usecase.PayParkingUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadParkingPayModule(): Module = module {
    factory {
        ParkingPayRemoteDataSourceImpl(
            api = get<HttpClient>().create(ParkingPayService::class.java),
        )
    }
    factory { ParkingPayRepositoryImpl(dataSource = get()) }
    factory { PayParkingUseCaseImpl(repository = get()) }
}
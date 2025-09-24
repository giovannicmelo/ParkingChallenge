package com.parafuso.parkingchallenge.feature.parkingout.di

import com.parafuso.parkingchallenge.core.data.remote.HttpClient
import com.parafuso.parkingchallenge.feature.parkingout.data.api.ParkingOutService
import com.parafuso.parkingchallenge.feature.parkingout.data.datasource.ParkingOutRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parkingout.data.repository.ParkingOutRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadParkingOutModule(): Module = module {
    factory {
        ParkingOutRemoteDataSourceImpl(
            api = get<HttpClient>().create(ParkingOutService::class.java),
        )
    }

    factory { ParkingOutRepositoryImpl(dataSource = get()) }
}
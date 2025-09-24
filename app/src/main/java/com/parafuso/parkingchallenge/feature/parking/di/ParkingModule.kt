package com.parafuso.parkingchallenge.feature.parking.di

import com.parafuso.parkingchallenge.core.data.remote.HttpClient
import com.parafuso.parkingchallenge.feature.parking.data.api.ParkingService
import com.parafuso.parkingchallenge.feature.parking.data.datasource.ParkingRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parking.data.mapper.ParkingMapper
import com.parafuso.parkingchallenge.feature.parking.data.repository.ParkingRepositoryImpl
import com.parafuso.parkingchallenge.feature.parking.domain.usecase.DoParkingUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadParkingModule(): Module = module {
    factory {
        ParkingRemoteDataSourceImpl(
            api = get<HttpClient>().create(ParkingService::class.java),
            mapper = ParkingMapper(),
        )
    }

    factory { ParkingRepositoryImpl(dataSource = get()) }
    factory { DoParkingUseCaseImpl(repository = get()) }
}
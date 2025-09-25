package com.parafuso.parkingchallenge.feature.parkinghistory.di

import com.parafuso.parkingchallenge.core.data.remote.HttpClient
import com.parafuso.parkingchallenge.feature.parkinghistory.data.api.ParkingHistoryService
import com.parafuso.parkingchallenge.feature.parkinghistory.data.datasource.ParkingHistoryRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parkinghistory.data.mapper.ParkingHistoryMapper
import com.parafuso.parkingchallenge.feature.parkinghistory.data.repository.ParkingHistoryRepositoryImpl
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.usecase.GetParkingHistoryUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadParkingHistoryModule(): Module = module {
    factory {
        ParkingHistoryRemoteDataSourceImpl(
            api = get<HttpClient>().create(ParkingHistoryService::class.java),
            mapper = ParkingHistoryMapper(),
        )
    }
    factory { ParkingHistoryRepositoryImpl(dataSource = get()) }
    factory { GetParkingHistoryUseCaseImpl(repository = get()) }
}
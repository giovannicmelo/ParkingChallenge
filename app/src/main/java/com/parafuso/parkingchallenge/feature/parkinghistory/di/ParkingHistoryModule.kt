package com.parafuso.parkingchallenge.feature.parkinghistory.di

import com.parafuso.parkingchallenge.core.data.remote.HttpClient
import com.parafuso.parkingchallenge.feature.parkinghistory.data.api.ParkingHistoryService
import com.parafuso.parkingchallenge.feature.parkinghistory.data.datasource.ParkingHistoryDataSource
import com.parafuso.parkingchallenge.feature.parkinghistory.data.datasource.ParkingHistoryRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parkinghistory.data.mapper.ParkingHistoryMapper
import com.parafuso.parkingchallenge.feature.parkinghistory.data.repository.ParkingHistoryRepositoryImpl
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.repository.ParkingHistoryRepository
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.usecase.GetParkingHistoryUseCase
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.usecase.GetParkingHistoryUseCaseImpl
import com.parafuso.parkingchallenge.feature.parkinghistory.presentation.activity.ParkingHistoryActivity
import com.parafuso.parkingchallenge.feature.parkinghistory.presentation.viewmodel.ParkingHistoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadParkingHistoryModule(): Module = module {
    factory<ParkingHistoryDataSource.Remote> {
        ParkingHistoryRemoteDataSourceImpl(
            api = get<HttpClient>().create(ParkingHistoryService::class.java),
            mapper = ParkingHistoryMapper(),
        )
    }
    factory<ParkingHistoryRepository> { ParkingHistoryRepositoryImpl(dataSource = get()) }
    factory<GetParkingHistoryUseCase> { GetParkingHistoryUseCaseImpl(repository = get()) }

    viewModel { (args: ParkingHistoryActivity.Args) ->
        ParkingHistoryViewModel(
            getParkingHistoryUseCase = get(),
            parkingHistoryActivityArgs = args,
        )
    }
}
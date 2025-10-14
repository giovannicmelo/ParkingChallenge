package com.parafuso.parkingchallenge.feature.parking.di

import com.parafuso.parkingchallenge.core.data.remote.HttpClient
import com.parafuso.parkingchallenge.feature.parking.data.api.ParkingService
import com.parafuso.parkingchallenge.feature.parking.data.datasource.ParkingDataSource
import com.parafuso.parkingchallenge.feature.parking.data.datasource.ParkingRemoteDataSourceImpl
import com.parafuso.parkingchallenge.feature.parking.data.mapper.ParkingMapper
import com.parafuso.parkingchallenge.feature.parking.data.repository.ParkingRepositoryImpl
import com.parafuso.parkingchallenge.feature.parking.domain.repository.ParkingRepository
import com.parafuso.parkingchallenge.feature.parking.domain.usecase.DoParkingUseCase
import com.parafuso.parkingchallenge.feature.parking.domain.usecase.DoParkingUseCaseImpl
import com.parafuso.parkingchallenge.core.domain.usecase.ValidatePlateUseCase
import com.parafuso.parkingchallenge.core.domain.usecase.ValidatePlateUseCaseImpl
import com.parafuso.parkingchallenge.feature.parking.presentation.viewmodel.ParkingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadParkingModule(): Module = module {
    factory<ParkingDataSource.Remote> {
        ParkingRemoteDataSourceImpl(
            api = get<HttpClient>().create(ParkingService::class.java),
            mapper = ParkingMapper(),
        )
    }

    factory<ParkingRepository> { ParkingRepositoryImpl(dataSource = get()) }
    factory<DoParkingUseCase> { DoParkingUseCaseImpl(repository = get()) }
    factory<ValidatePlateUseCase> { ValidatePlateUseCaseImpl() }

    viewModel { ParkingViewModel(doParkingUseCase = get(), validatePlateUseCase = get()) }
}
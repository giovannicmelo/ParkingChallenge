package com.parafuso.parkingchallenge.feature.parking.domain.usecase

import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking
import com.parafuso.parkingchallenge.feature.parking.domain.repository.ParkingRepository
import kotlinx.coroutines.flow.Flow

interface DoParkingUseCase {

    operator fun invoke(params: Params): Flow<Parking>
    data class Params(val plate: String)
}

class DoParkingUseCaseImpl(private val repository: ParkingRepository) : DoParkingUseCase {

    override fun invoke(params: DoParkingUseCase.Params): Flow<Parking> {
        return repository.doParking(params.plate)
    }
}
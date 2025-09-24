package com.parafuso.parkingchallenge.feature.parkingout.domain.usecase

import com.parafuso.parkingchallenge.feature.parkingout.domain.repository.ParkingOutRepository
import kotlinx.coroutines.flow.Flow

interface DoParkingOutUseCase {

    operator fun invoke(params: Params): Flow<Unit>
    data class Params(val plate: String)
}

class DoParkingOutUseCaseImpl(private val repository: ParkingOutRepository) : DoParkingOutUseCase {

    override fun invoke(params: DoParkingOutUseCase.Params): Flow<Unit> {
        return repository.doParkingOut(params.plate)
    }
}
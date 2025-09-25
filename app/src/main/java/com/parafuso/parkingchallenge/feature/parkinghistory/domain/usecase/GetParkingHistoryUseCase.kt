package com.parafuso.parkingchallenge.feature.parkinghistory.domain.usecase

import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.repository.ParkingHistoryRepository
import kotlinx.coroutines.flow.Flow

interface GetParkingHistoryUseCase {

    operator fun invoke(params: Params): Flow<List<ParkingHistory>>
    data class Params(val plate: String)
}

class GetParkingHistoryUseCaseImpl(
    private val repository: ParkingHistoryRepository
): GetParkingHistoryUseCase {

    override fun invoke(params: GetParkingHistoryUseCase.Params): Flow<List<ParkingHistory>> {
        return repository.getParkingHistory(params.plate)
    }
}
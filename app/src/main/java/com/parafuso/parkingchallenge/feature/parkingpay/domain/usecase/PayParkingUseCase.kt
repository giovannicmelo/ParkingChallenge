package com.parafuso.parkingchallenge.feature.parkingpay.domain.usecase

import com.parafuso.parkingchallenge.feature.parkingpay.domain.repository.ParkingPayRepository
import kotlinx.coroutines.flow.Flow

interface PayParkingUseCase {

    operator fun invoke(params: Params): Flow<Unit>
    data class Params(val plate: String)
}

class PayParkingUseCaseImpl(private val repository: ParkingPayRepository) : PayParkingUseCase {

    override fun invoke(params: PayParkingUseCase.Params): Flow<Unit> {
        return repository.payParking(params.plate)
    }
}
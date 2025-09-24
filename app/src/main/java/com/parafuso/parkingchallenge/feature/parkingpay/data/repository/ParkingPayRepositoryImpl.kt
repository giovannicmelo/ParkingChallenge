package com.parafuso.parkingchallenge.feature.parkingpay.data.repository

import com.parafuso.parkingchallenge.feature.parkingpay.data.datasource.ParkingPayDataSource
import com.parafuso.parkingchallenge.feature.parkingpay.domain.repository.ParkingPayRepository
import kotlinx.coroutines.flow.Flow

class ParkingPayRepositoryImpl(
    private val dataSource: ParkingPayDataSource.Remote
): ParkingPayRepository {

    override fun payParking(plate: String): Flow<Unit> {
        return dataSource.payParking(plate)
    }
}
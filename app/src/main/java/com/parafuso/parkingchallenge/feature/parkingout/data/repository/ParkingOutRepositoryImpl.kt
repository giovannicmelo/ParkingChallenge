package com.parafuso.parkingchallenge.feature.parkingout.data.repository

import com.parafuso.parkingchallenge.feature.parkingout.data.datasource.ParkingOutDataSource
import com.parafuso.parkingchallenge.feature.parkingout.domain.repository.ParkingOutRepository
import kotlinx.coroutines.flow.Flow

class ParkingOutRepositoryImpl(
    private val dataSource: ParkingOutDataSource.Remote
) : ParkingOutRepository {

    override fun doParkingOut(plate: String): Flow<Unit> {
        return dataSource.doParkingOut(plate)
    }
}
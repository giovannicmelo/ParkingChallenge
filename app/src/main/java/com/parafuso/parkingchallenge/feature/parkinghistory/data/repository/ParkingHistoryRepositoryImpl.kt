package com.parafuso.parkingchallenge.feature.parkinghistory.data.repository

import com.parafuso.parkingchallenge.feature.parkinghistory.data.datasource.ParkingHistoryDataSource
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.repository.ParkingHistoryRepository
import kotlinx.coroutines.flow.Flow

class ParkingHistoryRepositoryImpl(
    private val dataSource: ParkingHistoryDataSource.Remote
): ParkingHistoryRepository {

    override fun getParkingHistory(plate: String): Flow<List<ParkingHistory>> {
        return dataSource.getParkingHistory(plate)
    }
}
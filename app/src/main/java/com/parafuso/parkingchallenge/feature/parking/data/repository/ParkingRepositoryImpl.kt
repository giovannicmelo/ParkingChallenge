package com.parafuso.parkingchallenge.feature.parking.data.repository

import com.parafuso.parkingchallenge.feature.parking.data.datasource.ParkingDataSource
import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking
import com.parafuso.parkingchallenge.feature.parking.domain.repository.ParkingRepository
import kotlinx.coroutines.flow.Flow

class ParkingRepositoryImpl(private val dataSource: ParkingDataSource.Remote) : ParkingRepository {

    override fun doParking(plate: String): Flow<Parking> {
        return dataSource.doParking(plate)
    }
}
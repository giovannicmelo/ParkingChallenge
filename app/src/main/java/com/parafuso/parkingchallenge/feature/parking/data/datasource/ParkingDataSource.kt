package com.parafuso.parkingchallenge.feature.parking.data.datasource

import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking
import kotlinx.coroutines.flow.Flow

interface ParkingDataSource {

    interface Remote {

        fun doParking(plate: String): Flow<Parking>
    }
}
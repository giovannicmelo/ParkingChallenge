package com.parafuso.parkingchallenge.feature.parking.domain.repository

import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking
import kotlinx.coroutines.flow.Flow

interface ParkingRepository {

    fun doParking(plate: String): Flow<Parking>
}
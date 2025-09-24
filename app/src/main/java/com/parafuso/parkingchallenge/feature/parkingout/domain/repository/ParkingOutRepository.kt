package com.parafuso.parkingchallenge.feature.parkingout.domain.repository

import kotlinx.coroutines.flow.Flow

interface ParkingOutRepository {

    fun doParkingOut(plate: String): Flow<Unit>
}
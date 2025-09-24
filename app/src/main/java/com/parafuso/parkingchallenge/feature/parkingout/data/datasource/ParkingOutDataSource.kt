package com.parafuso.parkingchallenge.feature.parkingout.data.datasource

import kotlinx.coroutines.flow.Flow

interface ParkingOutDataSource {

    interface Remote {
        fun doParkingOut(plate: String): Flow<Unit>
    }
}
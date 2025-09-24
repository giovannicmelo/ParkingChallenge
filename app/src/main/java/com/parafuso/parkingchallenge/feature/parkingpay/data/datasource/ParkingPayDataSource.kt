package com.parafuso.parkingchallenge.feature.parkingpay.data.datasource

import kotlinx.coroutines.flow.Flow

interface ParkingPayDataSource {

    interface Remote {

        fun payParking(plate: String): Flow<Unit>
    }
}
package com.parafuso.parkingchallenge.feature.parkingpay.domain.repository

import kotlinx.coroutines.flow.Flow

interface ParkingPayRepository {

    fun payParking(plate: String): Flow<Unit>
}
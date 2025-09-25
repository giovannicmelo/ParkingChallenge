package com.parafuso.parkingchallenge.feature.parkinghistory.domain.repository

import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory
import kotlinx.coroutines.flow.Flow

interface ParkingHistoryRepository {

    fun getParkingHistory(plate: String): Flow<List<ParkingHistory>>
}
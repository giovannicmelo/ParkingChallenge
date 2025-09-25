package com.parafuso.parkingchallenge.feature.parkinghistory.data.datasource

import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory
import kotlinx.coroutines.flow.Flow

interface ParkingHistoryDataSource {

    interface Remote {

        fun getParkingHistory(plate: String): Flow<List<ParkingHistory>>
    }
}
package com.parafuso.parkingchallenge.feature.parkinghistory.data.mapper

import com.parafuso.parkingchallenge.core.data.extension.orFalse
import com.parafuso.parkingchallenge.core.data.mapper.Mapper
import com.parafuso.parkingchallenge.feature.parkinghistory.data.model.ParkingHistoryResponse
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory

class ParkingHistoryMapper : Mapper<ParkingHistoryResponse, ParkingHistory> {

    override fun map(source: ParkingHistoryResponse): ParkingHistory {
        return ParkingHistory(
            left = source.left.orFalse(),
            paid = source.paid.orFalse(),
            plate = source.plate.orEmpty(),
            reservation = source.reservation.orEmpty(),
            time = source.time.orEmpty(),
        )
    }
}
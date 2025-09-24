package com.parafuso.parkingchallenge.feature.parking.data.mapper

import com.parafuso.parkingchallenge.core.data.mapper.Mapper
import com.parafuso.parkingchallenge.core.data.util.orEmpty
import com.parafuso.parkingchallenge.feature.parking.data.model.ParkingResponse
import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking

class ParkingMapper : Mapper<ParkingResponse, Parking> {

    override fun map(source: ParkingResponse): Parking {
        return Parking(
            enteredAt = source.enteredAt.orEmpty(),
            plate = source.plate.orEmpty(),
            reservation = source.reservation.orEmpty(),
        )
    }
}
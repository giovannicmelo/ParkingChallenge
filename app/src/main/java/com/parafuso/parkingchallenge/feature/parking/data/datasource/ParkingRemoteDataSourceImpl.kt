package com.parafuso.parkingchallenge.feature.parking.data.datasource

import com.parafuso.parkingchallenge.core.data.mapper.Mapper
import com.parafuso.parkingchallenge.core.data.extension.parseHttpError
import com.parafuso.parkingchallenge.feature.parking.data.api.ParkingService
import com.parafuso.parkingchallenge.feature.parking.data.model.ParkingRequest
import com.parafuso.parkingchallenge.feature.parking.data.model.ParkingResponse
import com.parafuso.parkingchallenge.feature.parking.domain.model.Parking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ParkingRemoteDataSourceImpl(
    private val api: ParkingService,
    private val mapper: Mapper<ParkingResponse, Parking>,
) : ParkingDataSource.Remote {

    override fun doParking(plate: String): Flow<Parking> {
        val requestBody = ParkingRequest(plate)
        return flow { emit(api.postParking(requestBody)) }
            .parseHttpError()
            .map { mapper.map(it) }
    }
}
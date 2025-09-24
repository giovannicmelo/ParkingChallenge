package com.parafuso.parkingchallenge.feature.parkingout.data.datasource

import com.parafuso.parkingchallenge.core.data.extension.parseHttpError
import com.parafuso.parkingchallenge.core.data.remote.emptyBodyResponse
import com.parafuso.parkingchallenge.feature.parkingout.data.api.ParkingOutService
import kotlinx.coroutines.flow.Flow

class ParkingOutRemoteDataSourceImpl(
    private val api: ParkingOutService,
) : ParkingOutDataSource.Remote {

    override fun doParkingOut(plate: String): Flow<Unit> {
        return emptyBodyResponse { api.doParkingOut(plate) }.parseHttpError()
    }
}
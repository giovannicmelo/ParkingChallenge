package com.parafuso.parkingchallenge.feature.parkingpay.data.datasource

import com.parafuso.parkingchallenge.core.data.extension.parseHttpError
import com.parafuso.parkingchallenge.core.data.remote.emptyBodyResponse
import com.parafuso.parkingchallenge.feature.parkingpay.data.api.ParkingPayService
import kotlinx.coroutines.flow.Flow

class ParkingPayRemoteDataSourceImpl(
    private val api: ParkingPayService,
) : ParkingPayDataSource.Remote{

    override fun payParking(plate: String): Flow<Unit> {
        return emptyBodyResponse { api.payParking(plate) }.parseHttpError()
    }
}
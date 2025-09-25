package com.parafuso.parkingchallenge.feature.parkinghistory.data.datasource

import com.parafuso.parkingchallenge.core.data.extension.parseHttpError
import com.parafuso.parkingchallenge.core.data.mapper.Mapper
import com.parafuso.parkingchallenge.feature.parkinghistory.data.api.ParkingHistoryService
import com.parafuso.parkingchallenge.feature.parkinghistory.data.model.ParkingHistoryResponse
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class ParkingHistoryRemoteDataSourceImpl(
    private val api: ParkingHistoryService,
    private val mapper: Mapper<ParkingHistoryResponse, ParkingHistory>
): ParkingHistoryDataSource.Remote {

    override fun getParkingHistory(plate: String): Flow<List<ParkingHistory>> {
        return flow { emit(api.getParkingHistory(plate)) }
            .parseHttpError()
            .map { list -> list.map { mapper.map(it) } }
    }
}
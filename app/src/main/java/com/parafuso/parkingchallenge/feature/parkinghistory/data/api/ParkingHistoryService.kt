package com.parafuso.parkingchallenge.feature.parkinghistory.data.api

import com.parafuso.parkingchallenge.feature.parkinghistory.data.model.ParkingHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ParkingHistoryService {

    @GET("parking/{plate}")
    suspend fun getParkingHistory(@Path("plate") plate: String): List<ParkingHistoryResponse>
}
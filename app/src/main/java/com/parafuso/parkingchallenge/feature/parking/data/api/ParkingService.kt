package com.parafuso.parkingchallenge.feature.parking.data.api

import com.parafuso.parkingchallenge.feature.parking.data.model.ParkingRequest
import com.parafuso.parkingchallenge.feature.parking.data.model.ParkingResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ParkingService {

    @POST("parking")
    suspend fun postParking(@Body request: ParkingRequest): ParkingResponse
}
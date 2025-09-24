package com.parafuso.parkingchallenge.feature.parkingpay.data.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ParkingPayService {

    @POST("parking/{plate}/pay")
    suspend fun payParking(@Path("plate") plate: String): Response<Unit>
}
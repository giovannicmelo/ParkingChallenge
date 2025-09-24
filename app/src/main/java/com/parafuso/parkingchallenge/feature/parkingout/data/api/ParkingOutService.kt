package com.parafuso.parkingchallenge.feature.parkingout.data.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ParkingOutService {

    @POST("parking/{plate}/out")
    suspend fun doParkingOut(@Path("plate") plate: String): Response<Unit>
}
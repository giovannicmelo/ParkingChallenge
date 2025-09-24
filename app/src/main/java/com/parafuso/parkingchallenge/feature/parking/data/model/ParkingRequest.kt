package com.parafuso.parkingchallenge.feature.parking.data.model

import com.google.gson.annotations.SerializedName

data class ParkingRequest(
    @SerializedName("plate")
    val plate: String
)
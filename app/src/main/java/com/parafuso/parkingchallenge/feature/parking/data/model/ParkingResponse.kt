package com.parafuso.parkingchallenge.feature.parking.data.model

import com.google.gson.annotations.SerializedName

data class ParkingResponse(
    @SerializedName("entered_at")
    val enteredAt: String?,
    @SerializedName("plate")
    val plate: String?,
    @SerializedName("reservation")
    val reservation: String?
)
package com.parafuso.parkingchallenge.feature.parkinghistory.data.model

import com.google.gson.annotations.SerializedName

data class ParkingHistoryResponse(
    @SerializedName("left")
    val left: Boolean? = null,
    @SerializedName("paid")
    val paid: Boolean? = null,
    @SerializedName("plate")
    val plate: String? = null,
    @SerializedName("reservation")
    val reservation: String? = null,
    @SerializedName("time")
    val time: String? = null,
)